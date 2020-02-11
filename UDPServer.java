import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UDPServer
{
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


  public static void main(String args[]) throws Exception
  {
    int fPort = Integer.parseInt(args[0]);
    DatagramSocket serverSocket = new DatagramSocket(fPort);
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];
    String response = "0 \n";
    while(true)
    {
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);
      String sentence = new String( receivePacket.getData());
      System.out.println("RECEIVED: " + sentence);
      InetAddress IPAddress = receivePacket.getAddress();
      int port = receivePacket.getPort();
      if(isWebpage(sentence))
      {
        response = response.concat(ReadPage(sentence));
        if(response == "0 \n")
          response = "1 !!!No email address found on the page!!!";
      }
      else
      {
        response = "2 !!!Server couldn’t find the web page!!!";
      }
      sendData = response.getBytes();
      DatagramPacket sendPacket =
      new DatagramPacket(sendData, sendData.length, IPAddress, port);
      serverSocket.send(sendPacket);
      response = "0 \n";
    }
  }
}
