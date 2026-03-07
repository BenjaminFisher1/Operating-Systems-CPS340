import java.io.*;
public class PipeA
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		/*
			Byte-oriented:
				-most common for binary/general data
		*/
		// PipedOutputStream pout=new PipedOutputStream();
		// PipedInputStream pin=new PipedInputStream(pout); 
		// can also use pin.connect(pout);

		/*
			Character-oriented:
				-good when you're dealing with text/Readers/Writers
		*/
		PipedWriter pw=new PipedWriter();
		PipedReader pr=new PipedReader(pw);
		
		/**
			Goal: create a Hello World! pipe
			Try to create using a byte approach:
				Solution 1: Use PipedOutputStream, PipedInputStream, BufferedReader
			
			Try to create using a Character approach:
				Solution 2: Use PipedWriter, PipedReader
				
			Hint:
			Producer thread writes "Hello World!"
			Consumer thread reads everything and prints it	
		*/

		ProcessBuilder pb = new ProcessBuilder(
			"java", "-cp", System.getProperty("java.class.path"),
			PipeA.class.getName(), "--child"
		);

		pb.redirectInput(ProcessBuilder.Redirect.PIPE);
		pb.redirectOutput(ProcessBuilder.Redirect.PIPE);

		//Consumer thread
		Thread Consumer = new Thread(() -> {
			try (BufferedReader br = new BufferedReader(pr)){
				String line;
				//while pipe open
				while ((line = br.readLine()) != null) {
					System.out.println("Consumer: The Producer says: " + line);
				}
			} catch (IOException e) {
				System.err.println("Consumer errored:" + e.getMessage());
			}
		});
		
		Consumer.start();

		//Producer
		try( PrintWriter prw =  new PrintWriter(pw)){
			String msg = "Hello world!";
			System.out.println("Producer: I'm sending the message.");
			prw.println(msg);
		}
		
		//Wait for consumer to read
		try {Consumer.join();}catch (InterruptedException e){
			System.err.println("Consumer join errored:" + e.getMessage());
		};
		
	}
}