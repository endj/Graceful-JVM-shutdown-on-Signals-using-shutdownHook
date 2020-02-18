## Handling OS initated shutdown in Java gracefully using addShutdownHook 

This repository covers some patterns which can be used to gracefully handle shutdown which are initiated outside the JVM such as sending signals.


Note Runtime.halt(), SIGKILL (-9) signals and hardware failure can't be recovered using Runtime.shutdownHook.

### Covers:

Single threaded application

Single producer single consumer applications.

Single producer multiple consumer applications

