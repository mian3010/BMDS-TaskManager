package handinone;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author BieberFever
 * @author Claus
 *
 */
public enum TaskManagerTCPServer {
  INSTANCE;

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
        // Create a new thread for parsing the request
        RequestParser p = new RequestParser(con);
        // Start the request
        p.start();
      } catch (IOException e) {
        System.err.println(e);
      }
    }
	}
}
