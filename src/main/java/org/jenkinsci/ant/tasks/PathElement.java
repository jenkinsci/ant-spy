package org.jenkinsci.ant.tasks;

import org.jenkinsci.ant.AntElementProxy;

import java.io.File;

/**
 * @author Kohsuke Kawaguchi
 */
public interface PathElement extends AntElementProxy {
    File location();
    // path???
}
