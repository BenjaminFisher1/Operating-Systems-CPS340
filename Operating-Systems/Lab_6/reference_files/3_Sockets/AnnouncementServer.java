import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

public class AnnouncementServer {
    private static final int PORT = 51135;

    //max clients 5
    private static int THREAD_POOL_SIZE = 5;

    private static ExecutorService pool =
    Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server listening on 127.0.0.1:" + PORT);
        System.out.println("Thread pool size: " + THREAD_POOL_SIZE);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: "
                    + clientSocket.getInetAddress());

            ClientHandler handler = new ClientHandler(clientSocket);
            clients.add(handler);

            pool.execute(handler);
        }
    }
}

class ClientHandler implements Runnable {

    private Socket socket;
    private Random random = new Random();
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(
                    socket.getOutputStream(), true);    

            String line;

            while ((line = in.readLine()) != null) {

                if (line.equalsIgnoreCase("exit"))
                    break;

                String message = "Client" + socket.getPort() + ": " + line;

                for (ClientHandler client : AnnouncementServer.clients) {
                        client.sendAnnouncement(message);
                }
                
                System.out.println(message);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            AnnouncementServer.clients.remove(this);
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    public void sendAnnouncement(String message){
        out.println(message);
    }
}