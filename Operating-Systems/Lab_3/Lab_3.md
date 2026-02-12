Goals for this lab: ****
1. **Learn about the different CPU scheduling algorithms**
2. **Make observations about the pros/cons of each one**
3. **Compare the algorithms using different metrics**
4. **Implement some algorithms yourself**

First, we need to trace out some CPU scheduling examples on paper, including the Gannt chart, wait time, turnaround time, and response time for each process from sample data. 

I've placed scans of my papers where I did this in the same directory as this PDF in my repo.

This lab requires we  check our work with a java application to compare different CPU scheduling algorithms. I've setup java in an Ubuntu container.

First, I cloned the repo. Now, I have to compile all java files in `src` from the project.

![[Pasted image 20260212124806.png]]

We get a warning about "unchecked or unsafe input files". This is just a compiler warning. I'm not concerned because this is just for the lab, not for anything too serious.

Next, we are tasked to run java GUI. 

![[Pasted image 20260212125033.png]]

We can see it launched the GUI, just very very <sub>small.</sub> 

I can't figure out how to resize the size of the window. I was able to scale the interface in the window, but that doesn't really help if the window is tiny. So, my solution is to use the magnifier from accessibility features. 

![[Pasted image 20260212131211.png]]

It's a temporary solution but at least I can see what I'm doing.

Now, we have a nice area to experiment and check our work with different 

algorithms.

*I checked over my handwritten work and fixed any errors. The posted handwritten work is correct.*

### Observations:
Which algorithms are preemptive?
- SRT
- PSP

Which algorithms give the best/lowest average wait time?
- SJF
- SRT

Which algorithms give the best/lowest average turnaround time?
- SJF
- SRT

Which algorithms give the best/lowest average response time?
- Round Robin Quantum 2
- SJF
- SRT
- PSP

Which algorithm(s) give the most 'fair' access to each process to the CPU
- Round Robin

What is the response time for the non-preemptive algorithms?
- The same as the wait time.

What is the relationship between lowering wait times and turnaround times?
- When one is decreased, the other decreases.

Do you have any idea how to blend priority, fairness and turnaround times?
- Round Robin with an appropriate quantum time

Can you think of any other ways to do the scheduling?
- Perhaps a scheduling algorithm that functions like round robin, but has different queues for priorities with thresholds such as >5 goes in Q1, >3 goes in Q2, <=3 goes in Q3 etc.

### Implement
For this section, we attempt to alter the code of the Java GUI we were given to include new custom algorithms. Other students have reported many issues with this, so I don't fully expect to create a working product.




