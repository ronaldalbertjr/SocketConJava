import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCPServer extends Thread {
   private ServerSocket serverSocket;

   public TCPServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   // Check if URL is validoo
   public static Boolean isWebpage(String u){

       try{
           new URL(u).toURI();
           return true;
       }

       catch (Exception e){
           return false;
       }
   }


   // Read dat page!!!
   public static String ReadPage(String s) throws IOException {

       URL u = new URL(s);
       Boolean hasEmails = false;
       String returnString = "";

       BufferedReader in = new BufferedReader(
               new InputStreamReader(u.openStream()));

       String inLine;
       while ((inLine = in.readLine()) != null) {
           //System.out.println(inLine);

           Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
           Matcher matcher = pattern.matcher(inLine);

           while(matcher.find()){
               returnString = returnString.concat(matcher.group() + "\n");
           }

       }
       in.close();
       return returnString;
  }


   public void run() {
      while(true) {
         try {
            Socket server = serverSocket.accept();

            DataInputStream in = new DataInputStream(server.getInputStream());
            String sentence = in.readUTF();
            String response = "";

            if(isWebpage(sentence)) {
              response = ReadPage(sentence);
            }

            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(response);
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
