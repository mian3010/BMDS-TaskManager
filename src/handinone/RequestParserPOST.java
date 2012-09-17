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
public class RequestParserPOST extends RequestParser {
  public RequestParserPOST(Socket con, InetAddress source) throws IOException {
    super(con, source);
  }

  public void parseRequest(String request) throws IOException {
    TaskManagerTCPServer.log(source, request);
    out.writeUTF(request);
  }
}
