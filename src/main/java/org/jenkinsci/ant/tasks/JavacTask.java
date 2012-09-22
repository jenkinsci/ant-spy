package org.jenkinsci.ant.tasks;

import org.jenkinsci.ant.AntElementProxy;

import java.io.File;

/**
 * <a href="http://ant.apache.org/manual/Tasks/javac.html">Javac task</a>.
 *
 * @author Kohsuke Kawaguchi
 */
public interface JavacTask extends AntElementProxy {
    File srcdir();
    Path classpath();
}
