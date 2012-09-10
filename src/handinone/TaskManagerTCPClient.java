package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import Examples.SimpleTcpClient;

/**
 * @author BieberFever
 * @author Claus
 * 
 */
public class TaskManagerTCPClient {
  Socket socket;
  Scanner keyboard = new Scanner(System.in);

  public TaskManagerTCPClient(InetAddress inetAddress, int serverPort) {
    try {
      // Open a socket for communication.
      socket = new Socket(inetAddress, serverPort);

      // Get data output stream to send a String message to server.
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

      // Get the inputstream to receive data sent by server.
      InputStream is = socket.getInputStream();

      // based on the type of data we want to read, we will open suitbale
      // input stream.
      DataInputStream dis = new DataInputStream(is);

      // Send message
      String message = "Ping";
      dos.writeUTF(message);
      dos.flush();

      // Receive responce and print
      String responce = dis.readUTF();
      System.out.println("Message from server: " + responce);
      if (message.equals(responce))
        run();
      else {
        System.out.println("Server error");
        System.exit(0);
      }
    } catch (IOException ex) {
      Logger.getLogger(SimpleTcpClient.class.getName()).log(Level.SEVERE, null,
          ex);
      System.out.println("error message: " + ex.getMessage());
    }
  }

  private void run() {
    System.out.println("Connection created");
    System.out.println("Press Q to quit");

    while (true) {
      if (keyboard.hasNext()) {
        String text = keyboard.next().toLowerCase().trim();
        if (text.equals("q")) {
          stop();
        }
      }
    }
  }

  private void stop() {
    System.out.println("Program will now exit");
    // close the socket
    if (!socket.isClosed()) {
      try {
        socket.close();
      } catch (IOException e) {
        System.out.println("error message: " + e.getMessage());
      }
    }
    System.exit(0);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    int serverPort = 7896;
    InetAddress ip = null; // TODO something
    try {
      ip = InetAddress.getByName("localhost");
    } catch (UnknownHostException e) {
      System.err.println("error message: " + e.getMessage());
    }

    new TaskManagerTCPClient(ip, serverPort);
  }
}
