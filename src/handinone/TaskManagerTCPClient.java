package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

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
    String request = "post";
    if (check(request)) {
      // int id, String name, String date, String status, String description, String attendant
      System.out.println("Please enter the name of the task");
      String name = getString();
      System.out.println("Please enter a date for the task");
      String date = getString();
      System.out.println("Please enter status of the task");
      String status = getString();
      System.out.println("Please enter any description of the task");
      String description = getString();
      System.out.println("Please enter userID of the task attendant");
      int attendant = getInt();
      // Create task
      // ID is redefined by server
      Task task = new Task(0, name, date, status, description, attendant);
      // Save task
      ObjectMarshaller.marshall(task, dos);
      System.out.println("Task saved");
    }
    // Return
    run();
  }

  private void delete() {
    String request = "delete";
    // Check if server is ready for request
    if (check(request)) {
      // Get taskID from user
      System.out.println("Write taskID of the task you want to delete");
      int in = getInt();
      // Are you sure?
      System.out.println("Are you sure you want to delete? Y/N");
      while (true) {
        String line = getString();
        if (line.equals("n")) {
          run();
          break;
        } else if (line.equals("y")) {
          in = 0; // Don't delete anything
          break;
        }
      }
      // Delete task
      try {
        dos.writeInt(in);
      } catch (IOException e) {
        Log.error(e.getMessage());
      }
    }
    // Return
    run();
  }

  private void put() {
    String request = "put";
    // Check if server is ready for request
    if (check(request)) {
      // Get taskID from user
      int id = getInt();
      // Get task
      Task task = null;
      try {
        Calendar cal = (Calendar) ObjectMarshaller.getUnmarshaller(
            new Calendar()).unmarshal(dis);
        ArrayList<Task> tasks = cal.getTasks();
        if (tasks.isEmpty()) {
          System.out.println("No task by that ID");
          run();
        }
        task = tasks.get(0);
      } catch (JAXBException e) {
        Log.error(e.getMessage());
      }
      // List task as is
      System.out.println(task);
      // Which field does user want to edit?
      while (true) {
        System.out
            .println("Which field do you want to change? Write 0 to save");
        int in = getInt();
        if (in == 0)
          break;
      }
      // Send task to server
      ObjectMarshaller.marshall(task, dos);
      System.out.println("Task saved");
    }
    // Return
    run();
  }

  private void get() {
    String request = "get";
    // Check if server is ready for request
    if (check(request)) {
      // Get userID from user
      System.out.println("Type userID");
      int in = getInt();
      // Write userID to server
      try {
        dos.writeInt(in);
      } catch (IOException e) {
        Log.error(e.getMessage());
      }
      // Receive calendar with tasks
      ArrayList<Task> tasks = null;
      try {
        Calendar cal = (Calendar) ObjectMarshaller.getUnmarshaller(
            new Calendar()).unmarshal(dis);
        tasks = cal.getTasks();
      } catch (JAXBException e) {
        Log.error(e.getMessage());
      }
      // Print
      if (tasks != null) {
        if (tasks.size() == 0)
          System.out.println("No tasks");
        else {
          for (Task task : tasks) {
            System.out.println("\n" + task);
          }
        }
      }
    }
    // Return
    run();
  }

  /**
   * Get string from user
   * 
   * @return string
   */
  private String getString() {
    String input = keyboard.next().toLowerCase().trim();
    // Did user want to cancel?
    if (input.equals("q"))
      run();
    return input;
  }

  /**
   * Get integer from user
   * 
   * @return int
   */
  private int getInt() {
    String in = keyboard.next().toLowerCase().trim();
    int input = -1;
    while (true)
      try {
        input = Integer.parseInt(in);
        break;
      } catch (NumberFormatException e) {
        // Did the user want to cancel?
        if (in.equals("q"))
          run();
        System.out
            .println("Invalid userID. Please type a number or type Q to canel");
      }
    return input;
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

  private boolean check(String request) {
    String in = "";
    try {
      dos.writeUTF(request.trim().toLowerCase());
      dos.flush();

      in = dis.readUTF().trim().toLowerCase();
    } catch (IOException e) {
      Log.error(e.getMessage());
      return false;
    }
    if (in.equals(request))
      return true;
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
