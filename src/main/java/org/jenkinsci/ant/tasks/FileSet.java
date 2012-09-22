package org.jenkinsci.ant.tasks;

import org.jenkinsci.ant.AntElementProxy;

import java.io.File;

/**
 * Ant <a href="http://ant.apache.org/manual/Types/fileset.html">file set</a>
 *
 * @author Kohsuke Kawaguchi
 */
public interface FileSet extends AntElementProxy {
    /**
     * the root of the directory tree of this FileSet.
     */
    File dir();

    /**
     * shortcut for specifying a single-file fileset
     */
    File file();
}
