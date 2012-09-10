/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rao
 */
public class SimpleTcpServer {

    public static void main(String args[]) {
        
        
        try {
            int serverPort = 7896;

            // create a server socket listening at port 7896
            ServerSocket serverSocket = new ServerSocket(serverPort);

            System.out.println("Server started at 7896");

            // Server starts accepting requests.
            // This is blocking call, and it wont return, until there is request from a client.
            Socket socket = serverSocket.accept();


            // Get the inputstream to receive data sent by client. 
            InputStream is = socket.getInputStream();

            // based on the type of data we want to read, we will open suitbale input stream.  
            DataInputStream dis = new DataInputStream(is);

            // Read the String data sent by client at once using readUTF,
            // Note that read calls also blocking and wont return until we have some data sent by client. 
            String message = dis.readUTF(); // blocking call

            // Print the message.
            System.out.println("Message from Client: " + message);


            // Now the server switches to output mode delivering some message to client.
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF("Hello Client");

            outputStream.flush();

            socket.close();


        } catch (IOException ex) {
            Logger.getLogger(SimpleTcpServer.class.getName()).log(Level.SEVERE, null, ex);

            System.out.println("error message: " + ex.getMessage());
        }



    }
}