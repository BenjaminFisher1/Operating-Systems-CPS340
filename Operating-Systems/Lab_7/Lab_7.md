This lab is a lot of examining code with locks/mutex, semaphores, identifying critical sections, and avoiding race conditions and deadlock in programs.

## 1. What's Wrong? (Folder 1)
Each of the following shows mistakes that can happen when using threads and shared memory.  

For each one do the following:  
- i. Read over the code  
- ii. What do you think will happen?  
- iii. What is the problem?  
- iv. Compile, run and check  
- v. Can you fix it?

### Whatswrong1.java

I think this code is going to deadlock. 

The problem is the circular wait. Each thread depends on a lock the one before it holds.

Compile and run:
```bash
[b@ubuntu 1]$ javac whatswrong1.java 
📦[b@ubuntu 1]$ java whatswrong1 
```

After running this, the cursor blinks and the program hangs. Nothing is ever printed. This is because each thread depends on a lock another has to print, which stops any thread from printing.

I fixed it by adding another lock `lockD`, and making t3 use `lockD` instead of lockA. This eliminates the circular wait.

Fixed run:
```bash 
📦[b@ubuntu 1]$ javac whatswrong1.java 
📦[b@ubuntu 1]$ java whatswrong1 
Thread 3 running
Thread 2 running
Thread 1 running
```


### Whatswrong2.java

I think this code is supposed to count to 200000, but it's going to count inaccurately, either above or below the target, because it is being accessed by two threads at the same time with no synchronization.

```bash
📦[b@ubuntu 1]$ java whatswrong2
Count: 255
```

That's definitely not 200000. Running it again shows wildly different outputs, none of them correct.

```
[b@ubuntu 1]$ java whatswrong2
Count: 264
📦[b@ubuntu 1]$ java whatswrong2
Count: 339
📦[b@ubuntu 1]$ java whatswrong2
Count: 511
📦[b@ubuntu 1]$ java whatswrong2
Count: 1161
📦[b@ubuntu 1]$ java whatswrong2
Count: 121
📦[b@ubuntu 1]$ java whatswrong2
Count: 3057
```

I fixed it by joining the threads after they run, and by having another synchronized method to get the count.

Fixed run:

```bash
📦[b@ubuntu 1]$ javac whatswrong2.java 
📦[b@ubuntu 1]$ java whatswrong2
Count: 200000
```

Much better!

### Whatswrong3.java

This is very similar to my solution for whatswrong2, but the getCount() method is not synchronized. That is the issue.

```
[b@ubuntu 1]$ java whatswrong3
Count: 176444
📦[b@ubuntu 1]$ java whatswrong3
Count: 182855
```

We can see that running whatswrong3 is closer to 200000, but it's reading too early. There is a race condition between the writing to count and the read later on.

I'll fix it by just making getCount synchronized as i did in whatswrong2, and by making the increment function lock aroung `this` instead of the `lock` object.

```bash
📦[b@ubuntu 1]$ javac whatswrong3.java 
📦[b@ubuntu 1]$ java whatswrong3
Count: 200000
```

Perfect!

### whatswrong4.java

The issue here is that in increment, we lock, but never unlock. This program will hang!

Upon running, it hangs with no output. 

My fix is to simply change `increment()` to unlock after locking and incrementing the count:

```java
void increment()
{
	lock.lock();
	count++;
	lock.unlock();
}
```

```bash
📦[b@ubuntu 1]$ javac whatswrong4.java
📦[b@ubuntu 1]$ java whatswrong4
Count: 200000
```

Runs great! Also faster run than the others.


## 2. Semaphores (Codes in folder 2)  
For each, try in Java and C.  
Hint: if you don’t have C on your machine use your virtual machine.


### Semaphore1.java/Semaphore1.c - This creates a binary semaphore which behaves the same as a lock/mutex

These programs show how to use a Binary Semaphore to alternate two threads interacting with the same shared memory. 

The final count expected is 2000.

```bash
[b@ubuntu 2]$ java Semaphore1 
Final counter: 2000
```

The java program looks good!

```bash
[b@ubuntu 2]$ gcc Semaphore1.c -o Semaphore1 -pthread
📦[b@ubuntu 2]$ ./Semaphore1 
Final counter: 2000
```

c program also works well. 

### Semaphore2.java/Semaphore2.c - This creates a counting semaphore which behaves like a clerk giving out keys to rooms.

This program shows an example of using a semaphore with multiple keys in a thread.

I expect an output showing names of people wanting room keys, then receiving keys, and then leaving and returning. 

Each time we run this program, the output will change depending on the arrival of people.

```
[b@ubuntu 2]$ java Semaphore2 
Charlie wants a room key.
Alice wants a room key.
Alice got a key and entered a room.
Greg wants a room key.
Greg got a key and entered a room.
Bob wants a room key.
Eve wants a room key.
Faye wants a room key.
David wants a room key.
Charlie got a key and entered a room.
Greg is leaving room and returning key.
Alice is leaving room and returning key.
Bob got a key and entered a room.
Eve got a key and entered a room.
Charlie is leaving room and returning key.
Faye got a key and entered a room.
Eve is leaving room and returning key.
David got a key and entered a room.
Bob is leaving room and returning key.
Faye is leaving room and returning key.
David is leaving room and returning key.

```

Running the program again shows a different order of events.

```
[b@ubuntu 2]$ gcc Semaphore2.c -o Semaphore1 -pthread 
📦[b@ubuntu 2]$ ./Semaphore1 
Alice wants a room key.
Alice got a key and entered a room.
Bob wants a room key.
Bob got a key and entered a room.
David wants a room key.
David got a key and entered a room.
Faye wants a room key.
Greg wants a room key.
Charlie wants a room key.
Eve wants a room key.
David is leaving room and returning key.
Alice is leaving room and returning key.
Faye got a key and entered a room.
Bob is leaving room and returning key.
Greg got a key and entered a room.
Charlie got a key and entered a room.
Greg is leaving room and returning key.
Eve got a key and entered a room.
Faye is leaving room and returning key.
Charlie is leaving room and returning key.
Eve is leaving room and returning key.
```

C run shows this.

### Semaphore3.java/Semaphore3.c This creates a producer/consumer on a shared buffer.   The empty and full semaphores update to signify if there is a space or is something to read.

This code uses a bounded buffer and different semaphores to run a producer/consumer system.

I expect running this to produce and output numbers 0-10, then consume 0-10. 

```
b@ubuntu 2]$ java Semaphore3
Produced: 0
Produced: 1
Produced: 2
Produced: 3
Produced: 4
Consumed: 0
Consumed: 1
Consumed: 2
Consumed: 3
Consumed: 4
Produced: 5
Produced: 6
Produced: 7
Produced: 8
Produced: 9
Consumed: 5
Consumed: 6
Consumed: 7
Consumed: 8
Consumed: 9
```

Actually, the order produced and consumed is done in batches of 5 numbers, because of the buffer. 5 numbers are produced, then the consumer is allowed to consume 5 numbers.

#### What will happen if we start another consumer thread?
Starting another consumer thread hangs the program after all produced numbers are consumed, because the buffer is not filled and one of the consumer threads is still waiting.

#### What will happen if we start another producer thread?
The same thing happens here, there are numbers being produced without filling the buffer and this causes the program to hang

#### What will happen if we start another produce and consumer thread?
Starting a new consumer thread causes the buffer, producers, and consumers to function properly, and the program successfully completes.

## 3. Identifying critical sections (Codes in folder 3)

### Problem 1
The critical section is `buyTicket()`. If this is not synchronized, we can have race conditions occur where multiple threads try buying tickets at the same time, and the number of tickets decrements while another thread is checking the number.

```
javac Problem1.java 
📦[b@ubuntu 3]$ java Problem1
pool-1-thread-5 purchased a ticket. Remaining: 6
pool-1-thread-1 purchased a ticket. Remaining: 8
pool-1-thread-3 purchased a ticket. Remaining: 8
pool-1-thread-1 purchased a ticket. Remaining: 5
pool-1-thread-1 purchased a ticket. Remaining: 2
pool-1-thread-2 purchased a ticket. Remaining: 8
pool-1-thread-4 purchased a ticket. Remaining: 7
pool-1-thread-5 purchased a ticket. Remaining: 4
pool-1-thread-3 purchased a ticket. Remaining: 3
pool-1-thread-1 purchased a ticket. Remaining: 1
pool-1-thread-2 purchased a ticket. Remaining: 0
No tickets left.
Final tickets: 0
```


To fix this, I'm implementing a reentrant lock similar to the solution for whatswrong4.java

```
pool-1-thread-1 purchased a ticket. Remaining: 9
pool-1-thread-4 purchased a ticket. Remaining: 8
pool-1-thread-2 purchased a ticket. Remaining: 9
pool-1-thread-5 purchased a ticket. Remaining: 7
pool-1-thread-3 purchased a ticket. Remaining: 9
pool-1-thread-3 purchased a ticket. Remaining: 6
pool-1-thread-5 purchased a ticket. Remaining: 5
pool-1-thread-2 purchased a ticket. Remaining: 4
pool-1-thread-4 purchased a ticket. Remaining: 3
pool-1-thread-1 purchased a ticket. Remaining: 2
pool-1-thread-3 purchased a ticket. Remaining: 1
pool-1-thread-5 purchased a ticket. Remaining: 0
Final tickets: 0
```

### Problem 2

The race condition occurs here:

```java
void withdraw(int amount)

{
	if(balance>=amount)
{
	balance=balance-amount;
	System.out.println("Withdraw successful. Balance: " + balance);
}
else
	System.out.println("Not enough balance");
}
```

When multiple threads attempt to decrement/check the balance at the same time, a race condition occurs:

```
📦[b@ubuntu 3]$ java Problem2
Not enough balance
Withdraw successful. Balance: 300
Final Balance: 300
```

To fix, i've made a small change to make `withdraw()` a synchronized method, meaning only 1 thread can call `withdraw()` at a time. 

Fixed run:
```
[b@ubuntu 3]$ javac Problem2.java 
📦[b@ubuntu 3]$ java Problem2
Withdraw successful. Balance: 300
Not enough balance
Final Balance: 300
```

Fixed it! First thread withdraws 700, and when the second tries, it is told not enough balance, and we see the leftover balance.

### Problem 3

This program SHOULD output 1000000. However, a race condition here:

```java
static int sum=0;
static void add(int x)
{
	sum=sum+x;
}
```

where two threads can alter the same sum leading to x = x+ 5 rather than x = x + 5 + 5;

To avoid this race condition, I'm going to make each thread interact with a temporary sum, then join those temp sums to `Adder.sum` after they run their loops.

```
[b@ubuntu 3]$ javac Problem3.java 
📦[b@ubuntu 3]$ java Problem3
Final sum: 1000000
```

Perfect!


## 4. Sample Scenarios  
*Implement the following in Java or C. You want to apply the knowledge gained from the previous* *steps of this lab to get a code without race conditions and deadlocks present*

### a. Parking reservation simulation (limited resources problem)  
*Inputs: A parking lot with n parking spots.*  
*Constraints: Cars (threads) can enter only if a spot is free.*  
*Goal: use a counting semaphore to help track the availability of spot*s

This should be similar to the hotel keys problem.

```
📦[b@ubuntu Myfiles]$ java parking 
Car 4 wants a spot.
Car 2 wants a spot.
Car 5 wants a spot.
Car 2 got a spot and parked 
Car 1 wants a spot.
Car 6 wants a spot.
Car 3 wants a spot.
Car 4 got a spot and parked 
Car 5 got a spot and parked 
Car 5 leaving the lot and their spot is free
Car 2 leaving the lot and their spot is free
Car 1 got a spot and parked 
Car 4 leaving the lot and their spot is free
Car 6 got a spot and parked 
Car 3 got a spot and parked 
Car 6 leaving the lot and their spot is free
Car 1 leaving the lot and their spot is free
Car 3 leaving the lot and their spot is free
```

Looks good! 

### b. Restaurant simulation (variation of producer/consumer problem)
*Inputs: n chefs producing meals, m waiters consuming meals  
Constraints: limited number of serving trays (buffer)  
Goal: Implement this scenario making sure to avoid race conditions and deadlocks*

We can edit the code of Semaphore3 to implement this situation.

I've altered the code and used for loops to create n producer threads for chefs and m consumer threads for waiters. 

Trimmed output for 3 chefs 3 waiters

```
...
Consumed: 4
Chef Produced: 5
Chef Produced: 6
Chef Produced: 8
Consumed: 5
Chef Produced: 6
Consumed: 5
Consumed: 6
Consumed: 8
Consumed: 6
Chef Produced: 7
Chef Produced: 9
Chef Produced: 7
Chef Produced: 8
Chef Produced: 8
Consumed: 7
Consumed: 9
Consumed: 7
Consumed: 8
Consumed: 8
Chef Produced: 9
Chef Produced: 9
Consumed: 9
Consumed: 9
```


### c. Multiple Readers and Writers (extension of the reader/writer problem)
*Inputs: Multiple readers can read simultaneously (this doesn’t cause any issue). Writers  
require exclusive access to the shared resource.  
Constraints: You should have multiple threads as readers and multiple threads as writers

*Goal: use any of the techniques: atomics, locks/mutex, semaphores to get this working*  
*properly.*  
*Hint: You may create the buffer area similar to the one in Semaphore2.java example*  
*Challenge: You can use a shared file as the buffer area.*


 