
import java.net.*;
import java.io.*;

public class TCPClient{
  public static void main(String [] args){
    String serverName = args[0];
    int port = Integer.parseInt(args[1]);

    try{
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      String sentence = inFromUser.readLine();

      Socket client = new Socket(serverName, port);

      OutputStream outToServer = client.getOutputStream();
      DataOutputStream out = new DataOutputStream(outToServer);

      out.writeUTF(sentence);
      InputStream inFromServer = client.getInputStream();
      DataInputStream in = new DataInputStream(inFromServer);

      System.out.println(in.readUTF());
      client.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }
}
