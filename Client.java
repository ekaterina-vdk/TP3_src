import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 *
 * @author www.codejava.net
 */
public class Client {

    public static void main(String[] args) {
        //if (args.length < 2) return;

        //String hostname = args[0];
        //int port = Integer.parseInt(args[1]);

        String hostname = "127.0.0.1";
        int port = 6868;

        try (Socket socket = new Socket(hostname, port)) {
            SocketChannel chan = socket.getChannel();

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Console console = System.console();
            String text;
            System.out.println("Connection réussite!");
            do {
                Scanner user_input = new Scanner(System.in);
                text = user_input.nextLine();

                writer.println(text);
                System.out.println("client output: " + text);

                InputStream input = socket.getInputStream();

                byte[] data      = new byte[1024];
                int    bytesRead = input.read(data);
                short[] values;
                System.out.println("client output: ");
                while(bytesRead != -1) {
                    System.out.println((short)(bytesRead));

                    bytesRead = input.read(data);
                }
                input.close();


                //BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                //String time = reader.readLine();

                //System.out.println("client input: "+time);

            } while (true);

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}