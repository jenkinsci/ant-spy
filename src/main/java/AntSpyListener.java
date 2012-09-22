import org.jenkinsci.ant.AntListener;
import org.jenkinsci.ant.BuildListenerAdapter;
import org.jenkinsci.ant.ServiceLoader;

/**
 * {@link BuildListenerAdapter} that picks up {@link AntListener} from META-INF/services.
 *
 * <p>
 * Because this class is meant to be used in Ant command line option as "-listener AntSpyListener",
 * I'm putting this to the root package.
 *
 * @author Kohsuke Kawaguchi
 */
public class AntSpyListener extends BuildListenerAdapter {
    public AntSpyListener() {
        for (AntListener l : ServiceLoader.load(getClass().getClassLoader(),AntListener.class))
            addListener(l);
    }
}
