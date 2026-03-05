import java.io.*;
import java.net.*;

public class AnnouncementClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 51135;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(HOST, PORT);

        BufferedReader keyboard =
                new BufferedReader(new InputStreamReader(System.in));

        BufferedReader in =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Connected to 127.0.0.1:" + PORT);

        new Thread(() -> {
            String response;
            try {
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }).start();
        

        String input;

        while ((input = keyboard.readLine()) != null) {

            out.println(input);

            if (input.equalsIgnoreCase("exit"))
                break;

            String response = in.readLine();
            System.out.println("Server says: " + response);
        }

        socket.close();
    }
}