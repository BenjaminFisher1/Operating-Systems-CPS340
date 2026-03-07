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


### 4. Pipes
Files are in pipes folder.

#### Example 1:
- *Read over and run BytePipeExample.java*
	- This is basic producer/consumer pipe using Threads 
- Run with the 2 producers and 1 consumer
	```bash
	Consumer read: Line 1 from producer 2
	Consumer read: Line 1 from producer
	Consumer read: Line 2 from producer 2
	Consumer read: Line 2 from producer
	Consumer read: Line 3 from producer
	Consumer read: Line 3 from producer 2
	Consumer read: Line 4 from producer
	Consumer read: Line 4 from producer 2
	etc
	```
	
	
Here, the consumer double-reads for each line, getting one input from each producer.


- Run with 1 producer and 2 consumers
```bash
  Consumer read: Line 0 from producer
Consumer read: Line 1 from producer
Consumer2 read: Line 2 from producer
Consumer2 read: Line 3 from producer
Consumer read: Line 4 from producer
Consumer read: Line 5 from producer
Consumer read: Line 6 from producer
Consumer2 read: Line 7 from producer
etc
```

In this situation, the consumers fight over the data being sent by the producer, and it is split between them.

- Run with 2 producers and 2 consumers
	```bash 
		Consumer read: Line 3 from producer 2
		Consumer2 read: Line 4 from producer
		Consumer2 read: Line 4 from producer 2
		Consumer2 read: Line 5 from producer 2
		Consumer2 read: Line 5 from producer
		Consumer read: Line 6 from producer 2
		Consumer read: Line 6 from producer
		Consumer read: Line 7 from producer
		Consumer2 read: Line 7 from producer 2
		Consumer2 read: Line 8 from producer
		Consumer read: Line 8 from producer 2
		Consumer2 read: Line 9 from producer 2
		Consumer2 read: Line 9 from producer
		Consumer read: Line 10 from producer
		Consumer read: Line 10 from producer 2
		Consumer2 read: Line 11 from producer 2
		etc
	```

We can see the producers and consumers flip-flop between 1-1, 1-2, and 2-2 at random.

#### Example 2
*Read over and run CharacterPipeExample.java*

This is a basic producer/consumer pipe example without threads. We can see two different ways to create and connect a PipedWriter.

Run:
```bash
java CharacterPipeExample 
Read from PipedReader: Hello from PipedWriter
```

*Read over and run CharacterPipeExample2.java*

This is an example of a producer/consumer pipe using threads. 

Run:
```bash
java CharacterPipeExample2 
Reader thread received: Message from writer thread
```

#### Example 3
*In C we can create child processes and use pipes, see PipeD.c. In Java, we can ‘simulate’ this by using threads, see PipeD.java*

Comparing the two files, it seems like declaring pipes in C gives us more-fine grain control over pipes by being able to fork() directly from the process. In Java, we have to simulate this with a workaround such as spawning a thread for the child. 

*In Java, we can also use ProcessBuilder, see PipeD2.java*

ProcessBuilder is another fork() work around, but this one spawns the processes with their own heap, stack, etc, whereas manually spawning child threads will give them all a shared memory space.

This is cool, it looks like ProcessBuilder is safer to isolate child processes as opposed to Java's threads. 




### Practice

#### PipeA
*Fill in code to solve PipeA.java*
This is a simple Hello World program using pipes.

We have the option of solving using byte or Character approach. Since we're just printing Hello World, I'll go with the Character approach.

Need to user InterruptedException on consumer.join(), not IOException

```bash
 javac PipeA.java
PipeA.java:19: error: unreported exception IOException; must be caught or declared to be thrown
		PipedReader pr=new PipedReader(pw);
```

Tried fixing by wrapping pr constuctor in try catch but since pr used later in try catch for br this fails. Just going to put IOException in main.

Now we compile!

Let's run:

```
📦[b@ubuntu pipes]$ java PipeA
Producer: I'm sending the message.
Consumer: The Producer says: Hello world!
```

Awesome! It's working properly.
#### PipeB

PipeB chains multiple pipes to make a data pipeline.

Producer->Filter->Mapper->Consumer

My version of PipeB workflow:
- Producer pass numbers 1-10
- Filter only passes even nums
- Map squares each num
- Consumer prints each number it receives

```bash
📦[b@ubuntu pipes]$ java PipeB.java
FuncMapper errored:Write end dead
Consumer received: 0
Consumer received: 4
Consumer received: 16
Consumer received: 36
Consumer received: 64
Consumer errored:Write end dead
```

I think I have some sloppy logic with closing stuff up after finished working with it (hence the FuncMapper and Consumer errors) but otherwise this works great!
#### PipeC
- *Use pipes to read and write large amounts of data*
- *Experiment with different file sizes and buffer sizes and observe time changes*
- *Measure throughput in MB/s*

I'll have PipeC read data from a file called `data.txt`, which will contain a bunch of text data. 

Trying to reuse frame from PipeA for this.

I've got some junk data text files ranging from 10 mb to 100 mb filled with lorem ipsum.

10 mb file:
```
java PipeC
Producer: reading 'data_10mb.txt' (9 MB), buffer=8192 bytes
Throughput: 41.28 MB/s
Consumer: Lines Read: 91980
```


50 mb file:
```
java PipeC
Producer: reading 'data_50mb.txt' (49 MB), buffer=8192 bytes
Throughput: 77.04 MB/s
Consumer: Lines Read: 459901
```

100 mb file:
``` 
java PipeC
Producer: reading 'data_100mb.txt' (99 MB), buffer=8192 bytes
Throughput: 73.55 MB/s
Consumer: Lines Read: 919803

```


Let's shrink the buffer to 1 kb and rerun.


10 mb file:
```
java PipeC
Producer: reading 'data_10mb.txt' (9 MB), buffer=1024 bytes
Throughput: 42.25 MB/s
Consumer: Lines Read: 91980
```


50 mb file:

```
java PipeC
Producer: reading 'data_50mb.txt' (49 MB), buffer=1024 bytes
Throughput: 61.87 MB/s
Consumer: Lines Read: 459901
```


100 mb file:
```
java PipeC
Producer: reading 'data_100mb.txt' (99 MB), buffer=1024 bytes
Throughput: 75.92 MB/s
Consumer: Lines Read: 919803

```

We can see a smaller buffer size speeds up the 10 mb and 100 mb file reads, but slows down the 50 mb file read.

I looked into it, and this can be caused by the java compiler taking a while to fully optimize code. By 100 mb, the compiler is warmed up and optimizing efficiently, which explains the throughput increase from 50 mb to 100 mb.
#### Observe A,B,C,D,E

PipeA.c:
```
gcc PipeA.c -o a
📦[b@ubuntu pipes]$ ./a
Child received: Hello Child!
```

We can see a simple parent/child communication using forks in c.

PipeB.c
```
📦[b@ubuntu pipes]$ gcc PipeB.c -o b
📦[b@ubuntu pipes]$ ./b
Parent received: 14
```

PipeB uses a parent/child structure to do basic multiplication.

PipeC.c
```📦[b@ubuntu pipes]$ gcc PipeC.c -o c
📦[b@ubuntu pipes]$ ./c
Consumer got 0 squared = 0
Consumer got 3 squared = 9
Consumer got 0 squared = 0
Consumer got 9 squared = 81
Consumer got 1 squared = 1
Consumer got 2 squared = 4
Consumer got 7 squared = 49
Consumer got 7 squared = 49
Consumer got 0 squared = 0
Consumer got 3 squared = 9
```

This program is generating random numbers on the producer, and using the consumer to square and return them.

PipeD.c

```📦[b@ubuntu pipes]$ gcc PipeD.c -o d
📦[b@ubuntu pipes]$ ./d
[Parent] Sending number: 42
[Child] Received 42, sending back 84
[Parent] Received from child: 84
Communication complete.
```

Uses explicitly defined pipes to have parent send a number to child, child multiplies it by 2 and returns it.

PipeE.c

```
📦[b@ubuntu pipes]$ gcc PipeE.c -o e
📦[b@ubuntu pipes]$ ./e
Total sum from children: 28
```

This program spawns multiple children to add a list of numbers and report the result back to one place.