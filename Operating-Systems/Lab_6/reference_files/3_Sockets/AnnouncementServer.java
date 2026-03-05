import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnouncementServer {
    private static final int PORT = 51135;

    //max clients 5
    private static int THREAD_POOL_SIZE = 5;

    private static ExecutorService pool =
    Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server listening on 127.0.0.1:" + PORT);
        System.out.println("Thread pool size: " + THREAD_POOL_SIZE);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: "
                    + clientSocket.getInetAddress());

            pool.execute(new ClientHandler(clientSocket));
        }
    }
}