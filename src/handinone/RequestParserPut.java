package handinone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Update a task in the task list
 * 
 * @author Claus
 * 
 */
public class RequestParserPut extends RequestParser {
  public RequestParserPut(Socket con, InetAddress source) throws IOException {
    super(con, source);
  }

  public void run() {
    if (request != null) {
      try {
        parseRequest(request);
        out.flush();
        con.close();
      } catch (IOException e) {
        System.err.println(e);
      }
    }
  }

  public void parseRequest(String request) throws IOException {
    TaskManagerTCPServer.log(source, request);
    out.writeUTF(request);
  }
}
