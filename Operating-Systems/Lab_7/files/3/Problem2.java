class BankAccount 
{
    int balance=1000;
    synchronized void withdraw(int amount) 
	{
        if(balance>=amount)
		{
            balance=balance-amount;
            System.out.println("Withdraw successful. Balance: " + balance);
        }
		else 
            System.out.println("Not enough balance");
    }
}
public class Problem2
{
    public static void main(String[] args) throws InterruptedException
	{
        BankAccount account=new BankAccount();

        Thread t1 = new Thread(()->account.withdraw(700));

        Thread t2 = new Thread(()->account.withdraw(700));

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Final Balance: " + account.balance);
    }
}