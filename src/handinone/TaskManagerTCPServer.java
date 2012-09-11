package handinone;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

/**
 * @author BieberFever
 * @author Claus
 * 
 */
public enum TaskManagerTCPServer {
  INSTANCE;
  
  HashMap<InetAddress, String> commands = new HashMap<InetAddress, String>();

  /**
   * @author BieberFever
   * @param args
   */
  public static void main(String[] args) {
    TaskManagerTCPServer.INSTANCE.run(7896);
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
          String className = RequestParser.getClassName(commands.get(client));
          try {
            @SuppressWarnings("unchecked")
            Class<RequestParser> classDefinition = (Class<RequestParser>) Class.forName(className);
            @SuppressWarnings("rawtypes")
            Class[] constructorArgumentTypes = new Class[] {Socket.class, InetAddress.class};
            Object[] constructorArguments = new Object[] {con, client};
            Constructor<RequestParser> constructor = classDefinition.getConstructor(constructorArgumentTypes);
            RequestParser p = constructor.newInstance(constructorArguments);
            // Start the request
            p.start();
          } catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|SecurityException|NoSuchMethodException e) {
            e.printStackTrace();
          } catch (ClassNotFoundException e) {
            RequestParser.returnError(con, client); //Should not happen EVER
          }
        } else {
          String request = RequestParser.getRequest(con);
          try {
            @SuppressWarnings({ "unchecked", "unused" })
            Class<RequestParser> classDefinition = (Class<RequestParser>) Class.forName(RequestParser.getClassName(request));
            commands.put(client, request);
            DataOutputStream out = RequestParser.getOutputStream(con);
            RequestParser.writeUTF(out, request);
          } catch (ClassNotFoundException e) {
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
