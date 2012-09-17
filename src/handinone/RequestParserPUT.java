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
public class RequestParserPUT extends RequestParser {
  public RequestParserPUT(Socket con, InetAddress source) throws IOException {
    super(con, source);
  }

  public void parseRequest(String request) throws IOException {
    TaskManagerTCPServer.log(source, request);
    out.writeUTF(request);
  }
}
