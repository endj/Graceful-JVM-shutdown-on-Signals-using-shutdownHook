# Single Producer Thread Multiple Consumer Threads Execution Interrupt

In this example we have 1 producer thread and several consumer threads. 
We are using a bounded queue of size 10 with 5 consumers. Unlike the single producer
single consumer example, we now have several threads which need to be interrupted and we cant wait for any tasks to complete, including ongoing tasks.

Just like the single producer/consumer example, we will shutdown
the executor services and try to gracefully handle the ongoing tasks. Unlike the single producer/consumer example, 
we have written the tasks to be interruptible during execution which we will try to handle once they are interrupted.