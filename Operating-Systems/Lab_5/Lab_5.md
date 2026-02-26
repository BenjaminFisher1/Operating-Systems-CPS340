*This lab is a little lighter, likely because we have an exam tomorrow. The only goal for this lab is to get practice with Sockets in Java.*

We have a `file.zip` containing code for today's lab, so i'll unzip that in the folder for lab_5, and then jump into my ubuntu container set up with java. I open up the file in VSCode, and we can see it is divided into a few versions of a Client and Server pair, corresponding to steps in the lab.

![[Pasted image 20260226141625.png]]

#### Part a:
*Compile part a files, run server, and run client in a separate terminal. Observe what happens.*

Compiling:

![[Pasted image 20260226142912.png]]

Because I'm in a container, I'm using tmux to run separate terminals for Server and Client.

Run Server:
![[Pasted image 20260226143308.png]]

Run Client:
![[Pasted image 20260226143334.png]]

Oops! Our connection was refused. Let's check our client code:

>[!java] ClientA.java
>```java
>String host = "localhost";
>int port = 12345;
>```

There's the issue: Our client is attempting to connect to port 12345, and our server is listening on port 38885. 

Let's change ClientA.java to reflect this.

>[!java] ClientA.java
>```java
>String host = "localhost";
>int port = 38885;
>```

Now, I recompile ClientA.java, then rerun it.

Client:
![[Pasted image 20260226144006.png]]

Server:
![[Pasted image 20260226144036.png]]

Much better! Our connection was accepted, and we can see on both the client and server end, a random number was sent successfully from server to client.


#### Part b:
*Compile part b files, run server, run client.*

Compile and start server:
![[Pasted image 20260226144635.png]]

Run client:
![[Pasted image 20260226144754.png]]

Our client for part b is asked to submit a number, then the server will add a random number and return the sum.

*Run the server and try to run 2 clients in separate terminals.*

With the server running, I'll open the first instance of our Client: 

Client 1:
![[Pasted image 20260226150737.png]]

Then let's try to connect with the other, while the "Enter a number" I/O is hanging in Client1.

Client 2:
![[Pasted image 20260226150854.png]]

Looks like both clients connected okay, let's try sending numbers from both.

Client1:
![[Pasted image 20260226151112.png]]

On Client2, we are connected to the server, but no numbers are being sent to the client.
![[Pasted image 20260226151328.png]]

We can also see there is no record of data from Client2 on the server end either, just interactions with Client1.
![[Pasted image 20260226151359.png]]

#### Part c:

*Read over code, compile part c files, run server*

Upon reading the code for part c, it looks like the server has two I/O reads asking for two numbers to sum. 

Let's compile and run:
![[Pasted image 20260226152245.png]]
![[Pasted image 20260226152313.png]]

Now, we are instructed to start the server from part b:

![[Pasted image 20260226152530.png]]

We get an error:` Address already in use` because this server is trying to use the same port ServerC is listening on.

Now, same thing with server from part a:
![[Pasted image 20260226152638.png]]

No errors. ServerA has code to use a random available port:
>[!java] ServerA.java
> ```java
> // 0 = automatically assign random available port
ServerSocket serverSocket = new ServerSocket(0);
> ```

So we don't have to worry about port conflicts.

*Try changing the port to 8080. Does it work?*

I'll bring down ClientC and ServerC, then edit them to listen/connect on 8080.

ClientC:
![[Pasted image 20260226153025.png]]

ServerC:
![[Pasted image 20260226153135.png]]

Works properly on both client and server end.

#### part d:
*Read over the code, compile, run server, then run 4 separate clients.*

Looks like the code for ServerD handles each client in a new thread using a a class called `ClientHandler` that implements `Runnable`. 

Let's compile and get server going:
![[Pasted image 20260226153900.png]]

In each client window, I've entered the following:
![[Pasted image 20260226153818.png]]

Now, the server shows:
![[Pasted image 20260226153837.png]]

4 clients connected. Cool! 


#### part e:
*Read over the code, compare to part d, compile, run server, then run 4 clients.*

ServerE uses a thread pool, which ServerD does not.

Let's compile and run server:
![[Pasted image 20260226154419.png]]
Making note that the thread pool is only 3, and we are connecting 4 clients. I'm assuming one of our clients will be left out :(

In each Client:
![[Pasted image 20260226154537.png]]

We can see in ServerE, all clients are connected:
![[Pasted image 20260226154639.png]]

Let's try entering numbers on all 4.

Client 1:
![[Pasted image 20260226154735.png]]

Client 2:
![[Pasted image 20260226154759.png]]

Client 3:
![[Pasted image 20260226154814.png]]

Client 4:
![[Pasted image 20260226154849.png]]

We can see client 4 gets no response, because there are no threads left to run the computation. Even though the client connected, there isn't enough threads to share with it.


#### Bonus:
*How would we get socket communication working across two different machines?*
- We can run a client and server on both machines to listen and send data to eachother.

*What are the potential issues that can arise from this communication?*
- Dynamic IP address/port assignments could make this break frequently, and other people may be attempt to get into exposed ports.