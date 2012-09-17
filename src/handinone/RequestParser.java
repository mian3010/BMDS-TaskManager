package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public abstract class RequestParser extends Thread {
  protected String request;
  protected InetAddress source;

  protected Socket con;
  protected DataOutputStream out;
  protected DataInputStream dis;

  public RequestParser(Socket con, InetAddress source) throws IOException {
    this.con = con;
    this.source = source;
    out = getOutputStream(con);
    dis = new DataInputStream(con.getInputStream());
  }

  public static DataOutputStream getOutputStream(Socket con) throws IOException {
    return new DataOutputStream(con.getOutputStream());
  }

  public String getRequest(Socket con) throws IOException {
    return dis.readUTF();
  }

  public static void writeUTF(DataOutputStream out, String message)
      throws IOException {
    out.writeUTF(message);
    out.flush();
  }
  
  public static void returnError(Socket con, InetAddress client) throws IOException {
    RequestParser.writeUTF(RequestParser.getOutputStream(con), "Command not found");
  }
  
  public static String getClassName(String command) {
    return "handinone.RequestParser"+command.toUpperCase();
  }

  public void run() {
    while (true) {
      try {
        TaskManagerTCPServer.log(source, request);
        request = getRequest(con);
        if (request.equals("q")) throw new IllegalArgumentException("Quit detected");
        if (request != null) {
          parseRequest(request);
          out.flush();
        }
      } catch (IOException e) {
        System.err.println(e);
      }
    }
  }

  public abstract void parseRequest(String request) throws IOException;
}
