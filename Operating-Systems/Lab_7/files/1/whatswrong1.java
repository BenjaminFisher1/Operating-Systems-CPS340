public class whatswrong1
{
    static Object lockA=new Object();
    static Object lockB=new Object();
    static Object lockC=new Object();
    static Object lockD=new Object();

    public static void main(String[] args)
	{
        Thread t1=new Thread(()->{
            synchronized(lockA)
			{
                try 
				{
					Thread.sleep(100); 
				}catch(Exception e)
				{}
                synchronized(lockB)
				{
                    System.out.println("Thread 1 running");
                }
            }

        });

        Thread t2=new Thread(()->{
            synchronized(lockB)
			{
                try 
				{ 
					Thread.sleep(100);
				}catch(Exception e) 
				{}
                synchronized(lockC)
				{
                    System.out.println("Thread 2 running");
                }
            }

        });

        Thread t3=new Thread(()->{
            synchronized(lockC)
			{
                try 
				{ 
					Thread.sleep(100);
				}catch(Exception e)
				{}
                synchronized(lockD) 
				{
                    System.out.println("Thread 3 running");
                }
            }

        });

        t1.start();
        t2.start();
        t3.start();
    }
}