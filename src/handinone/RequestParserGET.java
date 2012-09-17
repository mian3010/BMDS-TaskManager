package handinone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Returns a list of tasks for the given user
 * 
 * @author BieberFever
 * @author Claus
 * 
 */
public class RequestParserGET extends RequestParser {
  public RequestParserGET(Socket con, InetAddress source) throws IOException {
    super(con, source);
  }

  public void parseRequest(String request) throws IOException {
    TaskManagerTCPServer.log(source, request);
    Calendar cal = TaskManagerTCPServer.INSTANCE.getCalendar(); 
    ArrayList<Task> taskList = cal.getListOfTasks(Integer.parseInt(request));
    ObjectMarshaller.marshall(taskList, out);
  }
}
