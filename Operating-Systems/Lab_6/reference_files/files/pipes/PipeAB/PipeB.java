import java.io.*;
public class PipeB
{
	public static void main(String[] args) throws IOException, InterruptedException{
		//Producer
		PipedWriter prodWriter = new PipedWriter();

		//filter
		PipedWriter filterWriter = new PipedWriter();
		PipedReader filterReader = new PipedReader(prodWriter);
		

		//funcMapper
		PipedWriter funcWriter = new PipedWriter();
		PipedReader funcReader = new PipedReader(filterWriter);
	

		//consumer (prints data)
		PipedWriter consWriter = new PipedWriter();
		PipedReader consReader = new PipedReader(funcWriter);
		

		ProcessBuilder pb = new ProcessBuilder(
			"java", "-cp", System.getProperty("java.class.path"),
			PipeB.class.getName(), "--child"
		);

		pb.redirectInput(ProcessBuilder.Redirect.PIPE);
		pb.redirectOutput(ProcessBuilder.Redirect.PIPE);

		//filter thread

		Thread filter = new Thread(() -> {
			try (BufferedReader br = new BufferedReader(filterReader)){
				String line;
				//filter by even nums
				while ((line = br.readLine()) != null) {
					int num = Integer.parseInt(line);
					if (num % 2 == 0) {
						filterWriter.write(line + "\n");
					}
				}

			} catch (IOException e){
					System.err.println("Filter errored:" + e.getMessage());
			}
		});

		//funcMapper thread
		Thread funcMapper = new Thread(() -> {
			try (BufferedReader br = new BufferedReader(funcReader)){
				String line;
				//map by squaring
				while ((line = br.readLine()) != null) {
					int num = Integer.parseInt(line);
					funcWriter.write((num * num) + "\n");
				}

			} catch (IOException e){
					System.err.println("FuncMapper errored:" + e.getMessage());
			}
		});

		//consumer thread
		Thread consumer = new Thread(() -> {
			try (BufferedReader br = new BufferedReader(consReader)){
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println("Consumer received: " + line);
				}

			} catch (IOException e){
					System.err.println("Consumer errored:" + e.getMessage());
			}
		});

		//Producer
		try(PrintWriter prw = new PrintWriter(prodWriter)){
			for (int i = 0; i < 10; i++) {
				prw.println(i);
			}
		}

		try {
			filter.start();
			funcMapper.start();
			consumer.start();

			filter.join();
			funcMapper.join();
			consumer.join();
		} catch (InterruptedException e) {
			System.err.println("Thread join errored:" + e.getMessage());
		}




	}
	/**
		Pipes can be chained together in a pipeline
		
		pipe 1 -> pipe 2 -> pipe 3 -> ... -> pipe n
		
		Note: This is very much like using streams with functional programming
		in Java 
		
		Goal: 
			1. Create a producer that writes lines of number data
			2. Create a filter that will apply some sort of filter to only
			   send some data foward. (ex. evens)
			3. Create a map that will apply some function 
			   to each data that it recieves. (ex. compute x*x for each data)
			4. Create a consumer that will read and write the final data
			
			one flow example: 
			producer generates 5 random values from [0,100]
			5, 10, 54, 87, 3
			filter keeps only evens
			10, 54
			map applys x*x to each 
			100, 2916
			consumer prints data it receives
			100, 2916
			
			Note: output of one is fed as input to next
	*/
}