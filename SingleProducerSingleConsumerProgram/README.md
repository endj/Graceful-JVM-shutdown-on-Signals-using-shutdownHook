# Single Producer Thread Single Consumer Thread Execution Interrupt

In this example we have 2 threads which we use to implement a producer-consumer pattern. 
We are running both threads using the ExecutorService API which simplifies some aspects.
In this case we are using singleThreadExecutors which are unbound task queues backed by 1 thread each.

The producer is enqueuing tasks at a rate higher than what the consumer can execute them which will builds a queue of tasks
which we attempt to handle gracefully once a terminate signal is received. The main goal with this shutdown hook
is to prevent any further tasks from being enqueued, complete any ongoing tasks and possibly handle any tasks
that where unable to complete. In this example we will let ongoing tasks complete without interrupting them for a while.




## NOTE!

### Executor Lifecycle
Unless we call shutdown or shutdownNow, generally an ExecutorService
will prevent the JVM from exiting even though there is "no more code" to run. See example:


```
public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> System.out.println("Java process still running"));
}
```
### Executor queue

By default, most ExecutorService implementations use unbound queues. This example is contrived on purpose and
is not suitable for any real producer-consumer situations.