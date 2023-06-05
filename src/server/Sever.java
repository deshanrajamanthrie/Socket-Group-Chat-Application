package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Sever {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        while (true){
            System.out.println("Waiting For a Clients...");
            Socket accept = serverSocket.accept();
            System.out.println("Client Connected !");


        }

    }
}
