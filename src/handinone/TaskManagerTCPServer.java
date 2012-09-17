package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

/**
 * @author BieberFever
 * @author Claus
 * 
 */
public enum TaskManagerTCPServer {
  INSTANCE;
  
  private HashMap<InetAddress, Class<RequestParser>> commands = new HashMap<>();
  private Calendar calendar = new Calendar();
  private static File calendarfile = new File("calendar.xml");

  /**
   * @author BieberFever
   * @param args
   */
  public static void main(String[] args) {
    try {
      TaskManagerTCPServer.INSTANCE.calendar = Calendar.loadCalendar(calendarfile);
      TaskManagerTCPServer.INSTANCE.run(7896);
    } catch (JAXBException|IOException e) {
      System.out.println("Could not load or create calendar file");
    }
  }
  
  public Calendar getCalendar() {
    return calendar;
  }

  public void run(int port) {
    ServerSocket ss = null;
    try {
      ss = new ServerSocket(port);
    } catch (IOException e) {
      System.err.println("Could not start server: " + e);
      System.exit(-1);
    }
    System.out.println("FileServer accepting connections on port " + port);

    while (true) {
      try {
        // Listens for request. It stays on this line until a request is made to
        // the server
        Socket con = ss.accept();
        InetAddress client = ss.getInetAddress();
        if (commands.containsKey(client)) {
          try {
            Class<RequestParser> classDefinition = commands.get(client);
            @SuppressWarnings("rawtypes")
            Class[] constructorArgumentTypes = new Class[] {Socket.class, InetAddress.class};
            Object[] constructorArguments = new Object[] {con, client};
            Constructor<RequestParser> constructor;
            constructor = classDefinition.getConstructor(constructorArgumentTypes);
            RequestParser p = constructor.newInstance(constructorArguments);
            
            // Start the request
            p.start();
            commands.remove(client);
          } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            RequestParser.returnError(con, client);
          }
        } else {
          DataInputStream dis = new DataInputStream(con.getInputStream());
          String request = dis.readUTF();
          log(client, request);
          try {
            @SuppressWarnings({ "unchecked" })
            Class<RequestParser> classDefinition = (Class<RequestParser>) Class.forName(RequestParser.getClassName(request));
            commands.put(client, classDefinition);
            DataOutputStream out = RequestParser.getOutputStream(con);
            RequestParser.writeUTF(out, request);
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
            RequestParser.returnError(con, client);
          }
        }
      } catch (IOException e) {
        System.err.println(e);
      }
    }
  }

  public static void log(InetAddress client, String msg) {
    System.err.println(new Date() + ": from " + client + " - " + msg);
  }
}
