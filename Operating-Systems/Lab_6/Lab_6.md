This lab is broken into Threads, Streams, Sockets, and Pipes in java.

### 1. Java Threads Examples:
*Read over examples and observe.*

#### Processors.java
Simple program to show available processors

#### MultithreadEx.java
Simple exampe on how to use and create threads in Java

#### MatrixMult_par.java
Spawns a thread for each row of matrix multiplication


### 2. Java Stream Example
#### StreamEx.java
Simple example of how we can use streams to filter a list of numbers by evens. Uses functional ideas / arrow funcs.


### 3.  More practice with Java Sockets
*Building off what you observed from last lab’s socket examples, try the following two  
socket programs*

#### Announcement System
*A server that will broadcast messages to multiple*  
*clients*  

▪ The server accepts multiple clients.  
▪ Any message the server receives is sent/broadcast to all clients.  
▪ Ex: There are A, B, C, D, E as clients.  
▪ Client A sends “Hi” to server.  
▪ Server sends “Hi” to B, C, D, E


Ideas:
Clienthandle implements runnable:
Threads spawned by pool/executor service contains submitted runnable (ClientHandler)
- we need to take a given thread and access the running clienthandler.