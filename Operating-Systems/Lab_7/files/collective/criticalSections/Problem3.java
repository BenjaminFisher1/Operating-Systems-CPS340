class Adder
{
    static int sum=0;
    static void add(int x)
	{
        sum=sum+x;
    }
}
public class Problem3
{
    public static void main(String[] args) throws InterruptedException
	{   
      
        Thread t1 = new Thread(() -> {
              int t1sum = 0;
            for (int i = 0; i < 100000; i++) {
                t1sum += 5;
            }
            Adder.add(t1sum);
        });

       
        Thread t2 = new Thread(() -> {
             int t2sum = 0;
            for (int i = 0; i < 100000; i++) {
                t2sum += 5;
            }
            Adder.add(t2sum);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();


        System.out.println("Final sum: " + Adder.sum);
    }
}