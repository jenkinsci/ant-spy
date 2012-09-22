package org.jenkinsci.ant;

import org.apache.tools.ant.Location;
import org.apache.tools.ant.Task;

/**
 * Wraps Ant {@link Task} and provides convenience methods.
 *
 * @author Kohsuke Kawaguchi
 */
public class AntTask {
    /**
     * This is what we are wrapping.
     */
    private final Task core;

    /**
     * A life of {@link AntTask} is scoped to one event callback.
     */
    private final AntEvent event;

    public AntTask(AntEvent event, Task core) {
        this.event = event;
        this.core = core;
    }

    /**
     * Introspect structure of Ant task invocation.
     */
    public AntElement getStructure() {
        return new AntElement(event,core.getRuntimeConfigurableWrapper());
    }

    public <T extends AntElementProxy> T getStructure(Class<T> type) {
        return getStructure().as(type);
    }

    public String getTaskName() {
        return core.getTaskName();
    }

    public Location getLocation() {
        return core.getLocation();
    }

    public String getDescription() {
        return core.getDescription();
    }
}
