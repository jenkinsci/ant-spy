package org.jenkinsci.ant;

import javax.annotation.Nonnull;

/**
 * Gets notified of the build in progress as {@link AntEvent}s.
 * 
 * @author Kohsuke Kawaguchi
 */
public abstract class AntListener {
    /**
     * Signals that a build has started. This event
     * is fired before any targets have started.
     *
     * @param event An event with any relevant extra information.
     */
    public void buildStarted(@Nonnull AntEvent event) {}

    /**
     * Signals that the last target has finished. This event
     * will still be fired if an error occurred during the build.
     *
     * @param event An event with any relevant extra information.
     *
     * @see AntEvent#getException()
     */
    public void buildFinished(@Nonnull AntEvent event) {}

    /**
     * Signals that a target is starting.
     *
     * @param event An event with any relevant extra information.
     *
     * @see AntEvent#getTarget()
     */
    public void targetStarted(@Nonnull AntEvent event) {}

    /**
     * Signals that a target has finished. This event will
     * still be fired if an error occurred during the build.
     *
     * @param event An event with any relevant extra information.
     *
     * @see AntEvent#getException()
     */
    public void targetFinished(@Nonnull AntEvent event) {}

    /**
     * Signals that a task is starting.
     *
     * @param event An event with any relevant extra information.
     *
     * @see AntEvent#getTask()
     */
    public void taskStarted(@Nonnull AntEvent event) {}

    /**
     * Signals that a task has finished. This event will still
     * be fired if an error occurred during the build.
     *
     * @param event An event with any relevant extra information.
     *
     * @see AntEvent#getException()
     */
    public void taskFinished(@Nonnull AntEvent event) {}

    /**
     * Signals a message logging event.
     *
     * @param event An event with any relevant extra information.
     *
     * @see AntEvent#getMessage()
     * @see AntEvent#getException()
     * @see AntEvent#getPriority()
     */
    public void messageLogged(@Nonnull AntEvent event) {}
}
