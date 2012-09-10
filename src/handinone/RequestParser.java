package handinone;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class RequestParser extends Thread {
  private String request;
  
  private Socket con;
  private InputStream is;
  private DataInputStream dis = null;
  private DataOutputStream out;
  
  public RequestParser(Socket con) throws IOException {
    this.con = con;
    
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
    out.writeUTF(request);
  }
}
