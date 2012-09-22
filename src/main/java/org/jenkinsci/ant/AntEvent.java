package org.jenkinsci.ant;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;

/**
 * Wraps {@link BuildEvent} and provides convenience methods.
 *
 * @author Kohsuke Kawaguchi
 */
public class AntEvent {
    private final BuildEvent event;
    private AntTask task;

    public AntEvent(BuildEvent event) {
        this.event = event;
    }

    public AntTask getTask() {
        if (task==null)
            task = new AntTask(this,event.getTask());
        return task;
    }

    public Project getProject() {
        return event.getProject();
    }

    public Target getTarget() {
        return event.getTarget();
    }

    public Throwable getException() {
        return event.getException();
    }

    public int getPriority() {
        return event.getPriority();
    }

    public String getMessage() {
        return event.getMessage();
    }

    public String getProperty(String name) {
        return getProject().getProperty(name);
    }
}
