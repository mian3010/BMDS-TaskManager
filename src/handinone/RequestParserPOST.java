package handinone;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.xml.bind.JAXBException;

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
    try {
      Task task = (Task) ObjectMarshaller.getUnmarshaller(Task.class).unmarshal(new ByteArrayInputStream(request.getBytes()));
      TaskManagerTCPServer.INSTANCE.getCalendar().addTask(task);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }
}
