# Ant Spy

This library provides a convenient wrapper around Ant build event notification so that programs can inject themselves into Ant invocation and get notified of what targets/tasks are running with what parameters.

Users would invoke Ant with the `-lib path/to/ant-spy.jar -listener AntSpyListener` command line option. Note that `ANT_ARGS` environment variable is a convenient way to insert these arguments without polluting the command line.

Your program implements `AntListener` and put a META-INF/services entry for your class. This class also needs to be passed to Ant invocation by the `-lib` option. See a sample code in `src/test/java` for more details.
