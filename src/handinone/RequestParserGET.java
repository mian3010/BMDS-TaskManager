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
    int id = Integer.parseInt(request);
    ArrayList<Task> taskList = cal.getListOfTasks(id);
    Calendar cal2 = new Calendar();
    cal2.addUser(cal.getUser(id));
    cal2.setTasks(taskList);
    ObjectMarshaller.marshall(cal2, out);
  }
}
