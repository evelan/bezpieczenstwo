package pl.pwr.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Evelan on 10/10/2016.
 */
public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        byte[] bytes = Files.readAllBytes(Paths.get("/Users/Evelan/Desktop/2.bmp"));

//        for (int i = 0; i < 14; i = i + 2) {
//            System.out.print(String.format("%02X ", bytes[i]));
//            System.out.println(String.format("%02X ", bytes[i + 1]));
//        }


        Socket socket = new Socket(host.getHostName(), 9876);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(bytes);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        String message = (String) objectInputStream.readObject();

        System.out.println("Message: " + message);
        objectInputStream.close();
        objectOutputStream.close();
    }
}