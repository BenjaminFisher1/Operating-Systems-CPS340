import java.util.concurrent.Semaphore;
import java.util.LinkedList;
public class restuarant
{
	//uses a linked list as our buffer area
    static LinkedList<Integer> buffer=new LinkedList<Integer>();

	//limits the size of the buffer NUMBER OF SERVING TRAYS
    static final int CAPACITY=5;

	//creates a counting semaphore to be able to give out CAPACITY/5 keys
    static Semaphore empty=new Semaphore(CAPACITY);
	//semaphore to represent a partially full/full buffer
	//starts as 0 for doesn't have anything
    static Semaphore full=new Semaphore(0);
	//Binary semaphore to represent a lock/mutex
    static Semaphore mutex=new Semaphore(1);

    public static void main(String[] args)
	{
        Runnable chef=() -> {
            for(int i=0;i<10;i++){
                try {
                    empty.acquire();//get a key/space in the buffer
                    mutex.acquire();//lock the shared data area
                    buffer.add(i);//put the value to the buffer
                    System.out.println("Chef Produced: " + i);
                } catch (InterruptedException e) {}
                finally {
                    mutex.release();//give back the key for buffer space
                    full.release();//buffer has something
                }
            }
        };
        Runnable waiter= () -> {
            for(int i=0;i<10;i++){
                try {
                    full.acquire();//take key if buffer has something
                    mutex.acquire();//lock the shared data
                    int item = buffer.removeFirst();//consume the value
                    System.out.println("Consumed: " + item);
                } catch (InterruptedException e) {}
                finally {
                    mutex.release();//unlock data
                    empty.release();//buffer has a free space
                }
            }
        };

        int n = 3; // number of chefs 
        for(int i = 0; i < n; i++){
            new Thread(chef).start();       
        }

        int m = 3; // number of waiters
        for(int i = 0; i < m; i++){
            new Thread(waiter).start();
        }

        
    }
}