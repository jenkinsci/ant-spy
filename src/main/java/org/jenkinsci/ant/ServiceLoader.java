package org.jenkinsci.ant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

/**
 * @author Kohsuke Kawaguchi
 */
public class ServiceLoader {
    public static <T> List<T> load(ClassLoader cl, Class<T> type) {
        List<T> impls = new ArrayList<T>();

        try {
            Enumeration<URL> en =  cl.getResources("META-INF/services/" + type.getName());
            while (en.hasMoreElements()) {
                URL url = en.nextElement();
                BufferedReader r = null;
                try {
                    r = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
                    String line;
                    while ((line=r.readLine())!=null) {
                        line = line.trim();
                        if (line.startsWith("#") || line.length()==0)   continue;

                        try {
                            Object impl = cl.loadClass(line).newInstance();
                            if (type.isInstance(impl))
                                impls.add(type.cast(impl));
                        } catch (InstantiationException e) {
                            LOGGER.log(WARNING, "Failed to instantiate "+line,e);
                        } catch (IllegalAccessException e) {
                            LOGGER.log(WARNING, "Failed to instantiate " + line, e);
                        } catch (ClassNotFoundException e) {
                            LOGGER.log(WARNING, "Failed to load " + line, e);
                        }
                    }
                } catch (IOException e) {
                    LOGGER.log(WARNING, "Failed to load "+url,e);
                } finally {
                    try {
                        if (r!=null)
                            r.close();
                    } catch (IOException _) {
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.log(WARNING, "Failed to list up /META-INF/services/"+type.getName(),e);
        }

        return impls;
    }

    private static final Logger LOGGER = Logger.getLogger(ServiceLoader.class.getName());
}
