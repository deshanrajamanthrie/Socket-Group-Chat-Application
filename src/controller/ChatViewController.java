package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;

public class ChatViewController extends Thread {
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
            System.out.println("Local Socket is connected with  Main Server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String msg = reader.readLine();
                String[] split = msg.split("");   //Split can return 0 length  string array
                String s = split[0];    //Index 0 array

                StringBuilder completemsg = new StringBuilder();    //these method can  be reprsesent capacity in maseges and initial capacity is 16
                for (int i = 0; i < s.length(); i++) {
                    completemsg.append(split[i] + "");
                }

                String[] split1 = msg.split("");
                String st = "";
                for (int i = 0; i < split1.length; i++) {
                    st += split1[i + 1] + "";
                }
                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);
                }
                if (firstChars.equalsIgnoreCase("img")) {
                    st = st.substring(3, st.length() - 1);

                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setFitHeight(100);

                    HBox hBox = new HBox(12);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!s.equalsIgnoreCase(lblName.getText())) {
                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        Text text1 = new Text("" + s + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);

                        Text text2 = new Text(" : Me");
                        hBox.getChildren().add(text2);
                    }
                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void sendOnAction(ActionEvent actionEvent) {

    }

    public void imageSendOnMouseclicked(MouseEvent mouseEvent) {

    }
}
