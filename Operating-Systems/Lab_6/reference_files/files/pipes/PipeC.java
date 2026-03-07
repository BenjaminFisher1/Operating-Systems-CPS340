/*
	This is a great way to practice:

Efficient reading of real files (buffered, chunked)
Piped streams for inter-thread handoff
Measuring throughput realistically
Handling large data without loading everything into memory
*/

import java.io.*;
public class PipeC{
	public static void main(String[] args) throws IOException, InterruptedException{
		final String filePath = "data_10mb.txt";

		final int bufferSize = 8192; 

		PipedWriter pw = new PipedWriter();
		PipedReader pr = new PipedReader(pw);

		//Consumer
		Thread consumer = new Thread(() -> {
			try (BufferedReader br = new BufferedReader(pr)){

				int linesRead = 0;
				while(br.readLine() != null){
					linesRead++;
				}
				System.out.println("Consumer: Lines Read: " + linesRead);
			} catch (IOException e){
				System.err.println("Consumer errored :(" + e.getMessage());
			}
			
		});

		consumer.start();


		//producer
		File file = new File(filePath);
		System.out.printf("Producer: reading '%s' (%d MB), buffer=%d bytes%n", filePath, file.length() / (1024 * 1024), bufferSize);

		long startTime = System.currentTimeMillis();

		try (BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);
			PrintWriter prw = new PrintWriter(new BufferedWriter(pw, bufferSize))){
				String line;
				while((line = br.readLine()) != null){
					prw.println(line);
				}
			}

			//measure throughput
			double seconds = (System.currentTimeMillis() - startTime) / 1000.0;
			long mbread = file.length() / (1048576);

			System.out.printf("Throughput: %.2f MB/s%n", mbread / seconds);

	}
}