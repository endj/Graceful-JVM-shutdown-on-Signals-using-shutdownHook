# Single Threaded Execution Interrupt

If the Java program receives a SIGKILL signal, it will terminate without any attempts at exiting gracefully. 
Signals such as SIGINT, SIGTERM, SIGHUP can be handled by registering a shutdown hook.

In this example we have a program that continuously executes some work on the main thread. 
In order to handle the shutdown gracefully, we check a flag between each tasks if the program has been asked to terminate.

The shutdown hook is invoked before the JVM shuts down allowing us in this case to finish the current task.

## Note!

Shutdown hooks are run **concurrently in no specific order**. If we replace the System.out with a log, we might not
se any logs cause the java.util.logging LogManager has its own shutdownHook which might run first. 

Reads and write of the **shouldRun** variable need to be volatile to ensure visibility from the main thread.