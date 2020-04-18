
import java.io.*;
import java.net.*;
import java.util.Date;

public class Gestion_serveur implements Runnable{
    private int port;

    public Gestion_serveur(int port){
        this.port = port;
    }
    public void run() {
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