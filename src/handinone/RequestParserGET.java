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
    Calendar cal = TaskManagerTCPServer.INSTANCE.getCalendar(); 
    int id = Integer.parseInt(request);
    try {
      Calendar cal2 = new Calendar();
      if (id > 0) {
        ArrayList<Task> taskList = cal.getListOfTasks(id);
        cal2.addUser(cal.getUser(id));
        cal2.setTasks(taskList);
        TaskManagerTCPServer.log(source, "GET: Returned "+taskList.size()+" task(s) for user "+id);
      } else {
        cal2 = cal;
        TaskManagerTCPServer.log(source, "GET: Returned "+cal2.getTasks().size()+" task(s) for all users"); //Potentially slow call
      }
      ObjectMarshaller.marshall(cal2, out);
    } catch (NullPointerException e) {
      TaskManagerTCPServer.log(source, "GET: No user found with id: "+id);
    }
  }
}
