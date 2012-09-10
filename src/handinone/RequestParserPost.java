package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Add a task to the task list
 * @author BieberFever
 * @author Claus
 *
 */
public class RequestParserPost extends RequestParser {
  private String request;
  private InetAddress source;

  private Socket con;
  private InputStream is;
  private DataInputStream dis;
  private DataOutputStream out;

  public RequestParserPost(Socket con, InetAddress source) throws IOException {
    this.con = con;
    this.source = source;

    is = con.getInputStream();
    dis = new DataInputStream(is);
    request = dis.readUTF();
    con.shutdownInput();
    out = new DataOutputStream(con.getOutputStream());
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