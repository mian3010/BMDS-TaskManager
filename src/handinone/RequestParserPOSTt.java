package handinone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Add a task to the task list
 * 
 * @author BieberFever
 * @author Claus
 * 
 */
public class RequestParserPOSTt extends RequestParser {
  public RequestParserPOSTt(Socket con, InetAddress source) throws IOException {
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
