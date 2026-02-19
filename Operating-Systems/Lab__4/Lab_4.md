
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

We're given the following rules for the MLFQ algorithm:

- **Rule 1:** If Priority(A) > Priority(B), A runs (B doesn’t).
- **Rule 2:** If Priority(A) = Priority(B), A & B run in round-robin fash-
ion using the time slice (quantum length) of the given queue.
- **Rule 3:** When a job enters the system, it is placed at the highest
priority (the topmost queue).
- **Rule 4:** Once a job uses up its time allotment at a given level (re-
gardless of how many times it has given up the CPU), its priority is
reduced (i.e., it moves down one queue).
- **Rule 5:** After some time period S, move all the jobs in the system
to the topmost queue.

Let's implement the MLFQ algorithm now.