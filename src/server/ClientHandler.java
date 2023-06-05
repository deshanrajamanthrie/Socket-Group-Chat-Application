package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {
    private ArrayList <ClientHandler>clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;


    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients){
        try {
            this.socket=socket;
            this.clients=clients;     //Include the clients in to the [Array List ArrayList<ClientHandler> clients]
            this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
