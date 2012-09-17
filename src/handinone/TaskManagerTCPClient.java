package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
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
  private Socket socket;
  private Scanner keyboard = new Scanner(System.in);
  private InputStream is;
  private DataInputStream dis;
  private DataOutputStream dos;
  private InetAddress inetAddress;
  private int serverPort;

  public TaskManagerTCPClient(InetAddress inetAddress, int serverPort) {
    this.inetAddress = inetAddress;
    this.serverPort = serverPort;
    initialize();
  }

  private void initialize() {
    try {
      // Open a socket for communication.
      socket = new Socket(inetAddress, serverPort);

      // Get data output stream to send a String message to server.
      dos = new DataOutputStream(socket.getOutputStream());

      // Get the inputstream to receive data sent by server.
      is = socket.getInputStream();

      // Create data input stream.
      dis = new DataInputStream(is);

      if (socket.isConnected())
        run();
      else {
        Log.error("Server error");
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
    System.out.println("Write QUIT to quit");
    System.out.println("Write GET to receive a list of tasks");
    System.out.println("Write PUT to change a task");
    System.out.println("Write DELETE to delete a task");
    System.out.println("Write POST to add a task");

    String in;
    while (true) {
      in = null; // reset

      // If user has input
      if (keyboard.hasNext()) {
        String text = keyboard.next().toLowerCase().trim();
        switch (text) {
        case "q":
        case "quit":
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
        case "reset":
        case "r":
          close();
          initialize();
          break;
        }
      }

      // If server has message
      try {
        if ((in = dis.readUTF()) != null) {
          in = "Message from server: " + in;
          System.out.println(in);
          Log.log(in);
        }
      } catch (IOException e) {
        Log.error(e.getMessage());
      }
    }
  }

  private void post() {
    System.out.println("Do something");
    String request = "POST";
    if(check(request)){
      //XML Shit
    }
  }

  private void delete() {
    System.out.println("Do something");
    String request = "DELETE";
    if(check(request)){
      //XML Shit
    }
  }

  private void put() {
    System.out.println("Do something");
    String request = "PUT";
    if(check(request)){
      //XML Shit
    }
  }

  private void get() {
    System.out.println("Do something");
    String request = "GET";
    if(check(request)){
      System.out.println("Type userID");
      String in = keyboard.next().toLowerCase().trim();
      int input;
      while (true)
        try{
          input = Integer.parseInt(in);
          break;
        }catch (NumberFormatException e){
          if(in.equals("q")) run();
          System.out.println("Invalid userID. Please type a number");
        }
      try {
        dos.write(input);
      } catch (IOException e) {
        Log.error(e.getMessage());
      }
      // XML Shit
      
      // Return
      run();
    }
  }

  private void close() {
    // close the socket
    if (!socket.isClosed()) {
      try {
        socket.close();
      } catch (IOException e) {
        System.out.println("error message: " + e.getMessage());
      }
    }
  }

  private boolean check(String request){
    String in = "";
    try {
      dos.writeUTF(request);
      dos.flush();
    
      in = dis.readUTF();
    } catch (IOException e) {
      Log.error(e.getMessage());
      return false;
    }
    if(in.equals(request)) return true;
    else {
      Log.error(in);
      return false;
    }
  }

  private void stop() {
    System.out.println("Program will now exit");
    // close the socket
    close();
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
