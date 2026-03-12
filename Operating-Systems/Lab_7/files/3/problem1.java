import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
class TicketSystem
{
    int tickets = 10;
    ReentrantLock lock = new ReentrantLock();
    void buyTicket() 
	{   
        lock.lock();
        try
        {
            if (tickets > 0){
                tickets--;
                System.out.println(Thread.currentThread().getName() + " purchased a ticket. Remaining: " + tickets);
            }
            else{
                System.out.println("No tickets left.");
            }
        }
        finally
        {
            lock.unlock();
        }
    }
}
public class Problem1
{
    public static void main(String[] args) throws InterruptedException
	{
        TicketSystem system=new TicketSystem();
		
		// Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Submit 12 tasks to simulate 12 buyers
        for(int i=1;i<=12;i++)
		{
			final int id=i;
            executor.submit(()->system.buyTicket(),"Thread"+id);
        }

        // Shutdown executor and wait for tasks to finish
        executor.shutdown();
        while (!executor.isTerminated())
		{
            // wait until all tasks complete
        }

        System.out.println("Final tickets: " + system.tickets);
    }
}