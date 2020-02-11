import java.net.*;
import java.io.*;

class UDPClient
{
   public static void main(String args[]) throws Exception
   {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);

      try{
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(serverName);
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println(modifiedSentence);
        clientSocket.close();
      }
      catch(IOException e){
        e.printStackTrace();
      }
   }
}
