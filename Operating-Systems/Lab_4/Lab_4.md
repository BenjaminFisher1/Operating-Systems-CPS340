
This lab builds off of lab 3, with the following goals:

1. Finish implementation of the two scheduling algorithms from lab 3
2. Solidify your observations and pros/cons of the scheduling algorithms
3. Implement the MLFQ algorithm (multilevel feedback queue)


### 1. Finish implementation of the two scheduling algorithms from lab 3

I implemented both scheduling algorithms in lab 3. 

### 2. Solidify your observations and pros/cons of the scheduling algorithms

I finished this in the previous lab as well.

### 3. Implement the MLFQ algorithm (multilevel feedback queue)

Before we jump into the implementation, we're instructed to do some reading of the chapters [here](https://pages.cs.wisc.edu/~remzi/OSTEP/cpu-sched-mlfq.pdf), and answer some questions:

- What is the runtime of a lookup in an array?
	- O(n) 
- What is the runtime of a lookup in an arraylist?
	- O(n)
- How is an array stored in memory?
	- Array is stored as consecutive memory of static size
- How is an arraylist stored in memory?
	- Arraylist is stored as consecutive memory of dynamic size
- Why was the hashmap data structure introduced?
	-  To pass the O(n) issues with arrays/arraylists by using hash function to achieve O(1) lookup.

MLFQ: 

- Why do you think the quantum times differ?
	- To optimize fairness and system performance
- What do you think the boost time is for?
	- To allow processes which have been stuck in low level queues to have a chance at high priority again
- Why is the lowest priority queue using FCFS?
	- Because the processes in this queue are known to take a long time, and a majority of processes should be completed before hitting the FCFS queue, it's okay if a low priority process gets stuck waiting for a while.

We're given the following rules for the MLFQ algorithm:

Let's implement the MLFQ algorithm now using these rules.

##### MLFQ Implementation

First, let's add MLFQ as an option on our dropdown menu:

```java
option = new JComboBox(new String[]{"FCFS", "SJF", "SRT", "PSN", "PSP", "RR", "LJF", "DPSN", "SPSN", "MLFQ"});
```

I also need to add a case to the switch statement:

```java
case "MLFQ":
	String tq2 = JOptionPane.showInputDialog("Time Quantum");
	if (tq2 == null) {
		return;
	}
	scheduler = new MultiLevelFeedbackQueue();
	scheduler.setTimeQuantum(Integer.parseInt(tq2));
	break;
```

This will prompt the user to insert a quantum time value upon selecting MLFQ.


Now, I'll make a new file `MultiLevelFeedbackQueue.java` and start implementing.

![[Pasted image 20260219165022.png]]
![[Pasted image 20260219165042.png]]
![[Pasted image 20260219165100.png]]![[Pasted image 20260219165116.png]]

I just screenshotted my code because copy pasting the text was messing up indentation.