package org.jenkinsci.ant;

import org.apache.tools.ant.RuntimeConfigurable;
import org.jvnet.tiger_types.Types;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Represents an XML element that constitute an Ant task invocation.
 *
 * @author Kohsuke Kawaguchi
 * @see AntElementProxy
 */
public class AntElement {
    /**
     * This is what we are wrapping.
     */
    private final RuntimeConfigurable core;

    /**
     * A life of {@link AntTask} is scoped to one event callback.
     */
    private final AntEvent event;

    public AntElement(AntEvent event, RuntimeConfigurable core) {
        this.core = core;
        this.event = event;
    }

    /**
     * Gets the element name.
     */
    public String getElementName() {
        return core.getElementTag();
    }

    public String getStringAttribute(String name) {
        return eval((String) core.getAttributeMap().get(name));
    }

    public File getFileAttribute(String name) {
        String v = getStringAttribute(name);
        if (v == null) return null;

        File f = new File(v);
        if (f.isAbsolute())     return f;

        return new File(event.getProperty("basedir"), v);
    }

    public AntElement getChildElement(String name) {
        Enumeration en = core.getChildren();
        while (en.hasMoreElements()) {
            RuntimeConfigurable child  = (RuntimeConfigurable)en.nextElement();
            if (child.getElementTag().equals(name))
                return new AntElement(event,child);
        }
        return null;
    }

    public List<AntElement> getChildElements(String name) {
        List<AntElement> r = new ArrayList<AntElement>();
        Enumeration en = core.getChildren();
        while (en.hasMoreElements()) {
            RuntimeConfigurable child  = (RuntimeConfigurable)en.nextElement();
            if (child.getElementTag().equals(name))
                r.add(new AntElement(event,child));
        }
        return r;
    }

    /**
     * Gets all the child elements of the same name, as a type-safe proxy.
     */
    public <T extends AntElementProxy> List<T> getChildElements(String name, Class<T> type) {
        List<T> r = new ArrayList<T>();
        for (AntElement t : getChildElements(name))
            r.add(t.as(type));
        return r;
    }


    /**
     * Evaluate and expands variable references.
     */
    private String eval(String val) {
        if (val==null)      return null;
        return event.getProject().replaceProperties(val);
    }

    /**
     * Wraps this {@link AntElement} into a type-safe proxy.
     */
    public <T extends AntElementProxy> T as(Class<T> type) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new InvocationHandlerImpl()));
    }

    private class InvocationHandlerImpl implements InvocationHandler, AntElementProxy {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getDeclaringClass()==Object.class) {// handle equals/hashCode methods on AntElement
                return method.invoke(AntElement.this,args);
            }

            if (method.getDeclaringClass()==AntElement.class) {// handle AntElement methods here
                return method.invoke(this,args);
            }

            String name = method.getName();
            Class type = method.getReturnType();

            if (type==String.class)     return getStringAttribute(name);
            if (type==File.class)       return getFileAttribute(name);
            if (type==AntElement.class)
                return getChildElement(name);
            if (AntElementProxy.class.isAssignableFrom(type)) {
                AntElement v = getChildElement(name);
                return v!=null ? v.as(type) : null;
            }
            if (type==List.class) {
                Class component = Types.erasure(Types.getTypeArgument(method.getGenericReturnType(),0));
                if (component==AntElement.class)
                    return getChildElements(name);
                if (AntElementProxy.class.isAssignableFrom(component))
                    return getChildElements(name,component);
            }

            throw new IllegalStateException("Unrecognized binding: "+method);
        }

        public AntElement unwrap() {
            return AntElement.this;
        }
    }
}
