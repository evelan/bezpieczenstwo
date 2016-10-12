package pl.pwr.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        int port = 9876;
        ServerSocket server = new ServerSocket(port);

        System.out.println("Waiting for client request");
        Socket socket = server.accept();

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        byte[] message = (byte[]) objectInputStream.readObject();
        System.out.println("Message Received");

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject("Thanks! File saved!");
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
        System.out.println("End of connection");
        server.close();
    }

}