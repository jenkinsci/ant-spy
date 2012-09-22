package org.jenkinsci.ant;

import org.jenkinsci.ant.tasks.JavacTask;

import java.lang.reflect.Proxy;

/**
 * Base type for the type-safe proxy interface to represent Ant tasks.
 *
 * <p>
 * First, for elements that appear in Ant build script, define interfaces that extends from this marker interface.
 * We call them "type-safe proxy interfaces". See {@link JavacTask} for example.
 *
 * <p>
 * A type-safe proxy interface is {@linkplain Proxy implemented at runtime dynamically}. Methods should be
 * defined for attributes and child elements. Return types must match the expected datatype of the attributes
 * and elements.
 *
 * @author Kohsuke Kawaguchi
 * @see AntElement
 */
public interface AntElementProxy {
    /**
     * Retrieves the underlying {@link AntElement} that this proxy represents.
     *
     * @see AntElement#as(Class)
     */
    AntElement unwrap();
}
