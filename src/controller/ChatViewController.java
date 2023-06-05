package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatViewController {
    public Label lblName;
    public TextField txtmsgtype;
    public VBox vBox;
    public AnchorPane chatViewcontext;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;


    public void initialize() {

        String userName = LoginFormController.userName;    //lbl to set the user Name
        lblName.setText(userName);
        try {
            socket = new Socket("localhost", 5001);
            System.out.println("Local Socket is connected with  Main Server");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendOnAction(ActionEvent actionEvent) {
    }

    public void imageSendOnMouseclicked(MouseEvent mouseEvent) {

    }
}
