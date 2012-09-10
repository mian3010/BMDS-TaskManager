package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public abstract class RequestParser extends Thread {
  protected String request;
  protected InetAddress source;

  protected Socket con;
  protected DataOutputStream out;

  public RequestParser(Socket con, InetAddress source) throws IOException {
    this.con = con;
    this.source = source;

    request = getRequest(con);
    out = getOutputStream(con);
  }

  public static DataOutputStream getOutputStream(Socket con) throws IOException {
    return new DataOutputStream(con.getOutputStream());
  }

  public static String getRequest(Socket con) throws IOException {
    InputStream is = con.getInputStream();
    DataInputStream dis = new DataInputStream(is);
    String request = dis.readUTF();
    con.shutdownInput();
    return request;
  }

  public static void writeUTF(DataOutputStream out, String message)
      throws IOException {
    out.writeUTF(message);
    out.flush();
  }

  public abstract void run();

  public abstract void parseRequest(String request) throws IOException;
}
