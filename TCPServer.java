import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCPServer extends Thread {
   private ServerSocket serverSocket;

   public TCPServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
   }

   // Check if URL is valid.
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

           Pattern pattern = Pattern.compile("([a-zA-Z0-9_.-]+)@([a-zA-Z0-9_.-]+[a-zA-Z])");
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
            String response = "0 \n";
            System.out.println("RECEIVED: " + sentence);
            if(isWebpage(sentence)) {
              response = response.concat(ReadPage(sentence));
              if(response == "0 \n")
                response = "1 !!!No email address found on the page!!!";
            }
            else
            {
              response = "2 !!!Server couldn’t find the web page!!!";
            }

            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(response);
            response = "0 \n";
            server.close();

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
