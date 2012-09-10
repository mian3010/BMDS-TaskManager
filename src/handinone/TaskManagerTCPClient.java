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
      System.err.println("Message from server: " + responce);
      if (message.equals(responce))
        run();
      else {
        System.err.println("Server error");
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
    System.out.println("Write GET to receive a list of tasks");
    System.out.println("Write PUT to change a task");
    System.out.println("Write DELETE to delete a task");
    System.out.println("Write POST to add a task");

    while (true) {
      if (keyboard.hasNext()) {
        String text = keyboard.next().toLowerCase().trim();
        switch (text){
        case "q":
          stop();
          break;
        case "get":
          get();
          break;
        case "put":
          put();
          break;
        case "delete":
          delete();
          break;
        case "post":
          post();
          break;
        }
      }
    }
  }

  private void post() {
    System.out.println("Do something");
  }

  private void delete() {
    System.out.println("Do something");
  }

  private void put() {
    System.out.println("Do something");
  }

  private void get() {
    System.out.println("Do something");
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
