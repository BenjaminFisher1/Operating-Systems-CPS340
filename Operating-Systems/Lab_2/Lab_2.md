# 1. Explore how processes are handled by the cpu

*Using your linux virtual machine, install the following: htop, make, code from ostep.*

### Install htop:
First things first, I run `sudo apt update` and `sudo apt upgrade` to make sure all packages are up to date. Next, we can install htop by running `sudo apt install build-essential manpages-dev htop strace`.

![[Pasted image 20260205141334.png]]

Looks great, htop is installed with version 3.3.0.

### Install make:
Next, I'll run `sudo apt install make`.
![[Pasted image 20260205141455.png]]

Perfect, we're all set with make as well. Onto ostep.

### Install ostep code:
We have to install the ostep code from a github repo, so I'll need to git as well. I already have git installed:

![[Pasted image 20260205141856.png]]

Next, I'll navigate to my Github folder, and clone the ostep repo. 

![[Pasted image 20260205142147.png]]

Now that we've cloned the repo, I'll cd into /ostep/intro and `make` to compile the intro c programs.

![[Pasted image 20260205142415.png]]

Great, we can see multiple programs compiled. The next step is to read through the intro chapter of the book and try out the code listed.

### ./cpu 

The first program is `cpu.c`. Let's examine the contents of this program with `nano cpu.c`: 

![[Pasted image 20260205142709.png]]
This program spins up a cpu that repeats the following:
- Check if 1 second has passed
- If one second has passed, print argument
First, let's test it with `./cpu ben`:
![[Pasted image 20260205142945.png]]
The program prints my name repeatedly until I kill it with ctrl+c.

Next, let's try running multiple programs at once with `./cpu A & ./cpu B`. & will send the process to the background.

![[Pasted image 20260205143449.png]]

This program repeatedly prints B, then A. This shows how virtualizing a cpu creates the illusion of the cpu doing multiple tasks at once. 

While running, we can use `jobs` to see ids of process running and their states.
If I run `ctrl+c`, then `jobs`, we can see ./cpu A is still running:

![[Pasted image 20260205144012.png]]

This process isn't killed by `ctrl+c` because it's in the background. To kill it, we must bring it to foreground with `fg 1`.

![[Pasted image 20260205144121.png]]

Now, we can kill it with `ctrl+c`.

*Question: How many programs can run in the foreground?*
A: With one cpu, only one program can run in the foreground.

### ./mem
Let's open up mem.c:
![[Pasted image 20260205145936.png]]

The mem.c program uses the `malloc` function to manually allocate memory. I've seen this function previously in my Assembly/Computer Architecture course. When running mem.c, the program:
- Allocates memory
- Prints address of allocated memory
- Puts zero into the first slot of the allocated memory.
This loops every second, and increments the value stored at the saved address, as well as printing the PID of itself.

Let's test it with `./mem 15`:
![[Pasted image 20260205150228.png]]
We can see the address of the memory we allocated, as well as watch the value of p increment, starting at 15.

What about with `./mem 15 & ./mem 5`?
![[Pasted image 20260205150443.png]]
We can see each instance of mem running has allocated to separate memory, and takes turns incrementing their value.

### htop
The first part of this section is to "start up a few cpus and mem in the background, then run htop in another terminal."

Because I'm running ubuntu in a container, I have to use a terminal multiplexer to have multiple terminal sessions. I'll be using tmux.

*Start up a few cpus and mem in the background*

I'll run `./mem 2 & ./cpu A`, then jump into `htop` in my other session.
![[Pasted image 20260205152647.png]]

`htop` opens us into a TUI that is a bit overwhelming at first glance: 
![[Pasted image 20260205152813.png]]

We can see `./mem and ./cpu` are sitting at the top of our process list. 
![[Pasted image 20260205152903.png]]

Let's try a few functions:

 `sort (f6)` 
![[Pasted image 20260205153021.png]]

We can see a nice menu of presets to sort by. Personally, I like keeping it on PERCENT_CPU. 

`tree (f5)`
![[Pasted image 20260205153235.png]]

`tree` shows us a parent/child tree of which processes spawned which. 


`filter (f4)` 
![[Pasted image 20260205153744.png]]

`filter` gives us the option to filter by keywords. I filtered for "cpu", and we can see `.cpu` is the sole display.

We're instructed to filter for ssh, because most people doing this assignment are using an Ubuntu VM. Because I'm using a distrobox container, not a VM, we actually don't have an ssh daemon running in the container.
