import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;

/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */
public class Server_Thread extends Thread {
    private Socket socket;

    public Server_Thread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            SocketChannel chan = socket.getChannel();

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);


            String text;

            do {
                text = reader.readLine();
                System.out.println("Server input: " + text);

                String server_output = "no you";
                writer.println(server_output);
                System.out.println("Server output: " + server_output);

            } while (!text.equals("stop"));

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}