
import java.io.*;
import java.net.*;
import java.util.Date;

public class Gestion_serveur {
    public static void main(String[] args) {
        //if (args.length < 1) return;

        //int port = Integer.parseInt(args[0]);
        int port = 6868;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new Server_Thread(socket).start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}