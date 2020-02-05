import java.net.*;
import java.io.*;

public class TCPServer extends Thread {
   private ServerSocket serverSocket;

   public TCPServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            Socket server = serverSocket.accept();

            DataInputStream in = new DataInputStream(server.getInputStream());
            String sentence = in.readUTF();

            System.out.println(sentence);

            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(sentence.toUpperCase());
            server.close();

         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }

   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      try {
         Thread t = new TCPServer(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
