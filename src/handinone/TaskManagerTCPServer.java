package handinone;

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
  
  private HashMap<InetAddress, RequestParser> commands = new HashMap<>();
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
          RequestParser p = commands.get(client);
          // Start the request
          p.start();
        } else {
          String request = RequestParser.getRequest(con);
          try {
            @SuppressWarnings({ "unchecked" })
            Class<RequestParser> classDefinition = (Class<RequestParser>) Class.forName(RequestParser.getClassName(request));
            @SuppressWarnings("rawtypes")
            Class[] constructorArgumentTypes = new Class[] {Socket.class, InetAddress.class};
            Object[] constructorArguments = new Object[] {con, client};
            Constructor<RequestParser> constructor = classDefinition.getConstructor(constructorArgumentTypes);
            RequestParser p = constructor.newInstance(constructorArguments);
            commands.put(client, p);
            DataOutputStream out = RequestParser.getOutputStream(con);
            RequestParser.writeUTF(out, request);
          } catch (ClassNotFoundException|NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
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
