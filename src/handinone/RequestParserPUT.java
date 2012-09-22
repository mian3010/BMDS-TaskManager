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
    int id = Integer.parseInt(request);
    Calendar cal = TaskManagerTCPServer.INSTANCE.getCalendar();
    Task task = cal.getTask(id);
    if (task != null) {
      ObjectMarshaller.marshall(task, out);
      TaskManagerTCPServer.log(source, "PUT: Returned task with id "+id);
    } else {
      TaskManagerTCPServer.log(source, "PUT: No task with id "+id+" exists");
      out.writeUTF("Error: No such task");
    }
    out.flush();
    request = dis.readUTF();
    
  }
}
