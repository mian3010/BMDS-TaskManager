package handinone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import Examples.SimpleTcpClient;

/**
 * @author BieberFever
 * @author Claus
 * 
 */
public class TaskManagerTCPClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// IP address of the server,
			InetAddress serverAddress = InetAddress.getByName("localhost");

			// It is the same port where server will be listening.
			int serverPort = 7896;

			// Open a socket for communication.
			Socket socket = new Socket(serverAddress, serverPort);

			// Get data output stream to send a String message to server.
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());

			// Get the inputstream to receive data sent by server.
			InputStream is = socket.getInputStream();

			// based on the type of data we want to read, we will open suitbale
			// input stream.
			DataInputStream dis = new DataInputStream(is);

			// Send message
			String message = "Ping";
			dos.writeUTF(message);
			dos.flush();
			
			// Receive responce and print
			String responce = dis.readUTF();
			System.out.println("Message from server: " + responce);

			// Finnaly close the socket.
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(SimpleTcpClient.class.getName()).log(Level.SEVERE,
					null, ex);

			System.out.println("error message: " + ex.getMessage());
		}
	}

}
