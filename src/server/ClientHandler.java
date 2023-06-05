package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private ArrayList<ClientHandler> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;


    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        try {
            this.socket = socket;
            this.clients = clients;     //Include the clients in to the [Array List ArrayList<ClientHandler> clients]
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));   //read the input msg
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                if (msg.equalsIgnoreCase("finished")) {
                    break;
                }
                for (ClientHandler cl : clients) {
                    cl.writer.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
