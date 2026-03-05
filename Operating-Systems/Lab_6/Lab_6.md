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

My server clients connection is sort of working, they can see messages from eachother, but client A will only see messages from client B if client A sends a message first. I think this is happening because the client waits for user input from the client before loading responses (artifact of copying some code from prev examples for 1to1 communication.)

It's working now!! But it dies eventually :(


#### Number Guessing Game
*A server that will play a number guessing game with*  
*each client connected.*  
*▪ The server picks a random secret number (1–100) per client.*  
*▪ Client guesses repeatedly.*  
*▪ Server replies with hints, for example:*

*• "Too high"*  
*• "Too low"*  
*• "Correct! Took X guesses."*  
*▪ Each client plays independently from other clients*

I think I can reuse logic from lab 5 server/client E, which was designed for independent summing of numbers from client to server.

Got it working!

Snippet:
```bash
Server says: Too high!!! 
92
Server says: YOU GUESSED THE NUMBER
```


#### 4. Pipes
Files are in pipes folder.

### Example 1:
- *Read over and run BytePipeExample.java*
