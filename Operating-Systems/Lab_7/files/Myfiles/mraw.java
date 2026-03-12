import java.util.concurrent.Semaphore;
public class mraw
{
    static final Semaphore mutex = new Semaphore(1);      // protects readCount
    static final Semaphore writeLock = new Semaphore(1);  // exclusive writer lock
    static int readCount = 0;

    // Shared buffer area
    static final StringBuilder buffer = new StringBuilder("buffer: ");

    static class Person implements Runnable
	{
        private final String name;
        private final boolean isWriter;

        Person(String name)
		{
			this(name, false);
		}

        Person(String name, boolean isWriter)
		{ 
			this.name = name;
			this.isWriter = isWriter;
		}

        @Override
        public void run()
		{
            try 
			{
                if (isWriter)
				{
                    writeLock.acquire();
                    String entry = " | " + name + " wrote" + System.currentTimeMillis();
                    buffer.append(entry);
                    System.out.println(name + " wrote to buffer.");
                    Thread.sleep(600);
                    writeLock.release();
				}
				else
				{
                    mutex.acquire();
                    readCount++;
                    if (readCount == 1)
					{
                        writeLock.acquire(); // first reader blocks writers
					}
                    mutex.release();

                    System.out.println(name + " read: " + buffer.toString());
                    Thread.sleep(400);

                    mutex.acquire();
                    readCount--;
                    if (readCount == 0)
					{
                        writeLock.release(); // last reader allows writers
					}
                    mutex.release();
				}
            } catch (InterruptedException e)
			{
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args)
	{
        String[] readers = {"R1", "R2", "R3", "R4", "R5"};
        String[] writers = {"W1", "W2", "W3"};

        //initialize reader threads
        for(String name : readers)
		{
            new Thread(new Person(name, false)).start();
		}

        //initialize writer threads
        for(String name : writers)
		{
            new Thread(new Person(name, true)).start();
        }
    }
}