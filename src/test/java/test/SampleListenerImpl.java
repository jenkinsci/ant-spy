package test;

import org.jenkinsci.ant.AntEvent;
import org.jenkinsci.ant.AntListener;
import org.jenkinsci.ant.AntTask;
import org.jenkinsci.ant.tasks.JavacTask;
import org.kohsuke.MetaInfServices;

import javax.annotation.Nonnull;

/**
 * @author Kohsuke Kawaguchi
 */
@MetaInfServices
public class SampleListenerImpl extends AntListener {
    @Override
    public void taskFinished(@Nonnull AntEvent event) {
        AntTask t = event.getTask();
        if (t.getTaskName().equals("javac")) {
            System.out.println("srcdir="+t.getStructure(JavacTask.class).srcdir());
        }
    }
}
