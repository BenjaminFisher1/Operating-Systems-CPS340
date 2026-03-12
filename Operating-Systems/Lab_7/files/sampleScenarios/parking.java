import java.util.concurrent.Semaphore;
public class parking
{
	//constant for how many keys/spots are available
    static final int KEYS=3;
	//counting semaphore to controll giving out the spots
    static Semaphore keysDesk=new Semaphore(KEYS);

    static class Car implements Runnable
	{
        private final String name;
        Car(String name)
		{ 
			this.name=name;
		}
        @Override
        public void run()
		{
            try 
			{
                System.out.println(name+" wants a spot.");
                keysDesk.acquire(); //give out a key
                System.out.println(name+" got a spot and parked ");
                Thread.sleep(2000); //simulate time in room
                System.out.println(name + " leaving the lot and their spot is free");
                keysDesk.release(); //return key to desk
            } catch (InterruptedException e)
			{
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args)
	{
        int cars = 6; // cars who need to park
        for(int i = 0; i < cars; i++) 
		{
            new Thread(new Car("Car " + (i + 1))).start();
        }
    }
}