package org.jenkinsci.ant.tasks;

import org.jenkinsci.ant.AntElementProxy;

import java.util.List;

/**
 * Represents a <a href="http://ant.apache.org/manual/using.html#path">path-like structure</a>.
 *
 * @author Kohsuke Kawaguchi
 */
public interface Path extends AntElementProxy {
    List<PathElement> pathelement();
    List<FileSet> fileset();
}
