package org.jenkinsci.ant;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Receives build events as {@link BuildListener}
 * and forwards them to {@link AntListener}.
 *
 * @author Kohsuke Kawaguchi
 */
public class BuildListenerAdapter implements BuildListener {
    private final List<AntListener> listeners = new CopyOnWriteArrayList<AntListener>();

    public void addListener(AntListener l) {
        listeners.add(l);
    }

    public void removeListener(AntListener l) {
        listeners.remove(l);
    }

    public void buildStarted(BuildEvent event) {
        AntEvent ev = new AntEvent(event);
        for (AntListener l : listeners)
            l.buildStarted(ev);
    }

    public void buildFinished(BuildEvent event) {
        AntEvent ev = new AntEvent(event);
        for (AntListener l : listeners)
            l.buildFinished(ev);
    }

    public void targetStarted(BuildEvent event) {
        AntEvent ev = new AntEvent(event);
        for (AntListener l : listeners)
            l.targetStarted(ev);
    }

    public void targetFinished(BuildEvent event) {
        AntEvent ev = new AntEvent(event);
        for (AntListener l : listeners)
            l.targetFinished(ev);
    }

    public void taskStarted(BuildEvent event) {
        AntEvent ev = new AntEvent(event);
        for (AntListener l : listeners)
            l.taskStarted(ev);
    }

    public void taskFinished(BuildEvent event) {
        AntEvent ev = new AntEvent(event);
        for (AntListener l : listeners)
            l.taskFinished(ev);
    }

    public void messageLogged(BuildEvent event) {
        AntEvent ev = new AntEvent(event);
        for (AntListener l : listeners)
            l.messageLogged(ev);
    }
}