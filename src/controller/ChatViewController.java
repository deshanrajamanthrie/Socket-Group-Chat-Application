package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Locale;

public class ChatViewController extends Thread {
    public Label lblName;
    public TextField txtmsgtype;
    public VBox vBox;
    public AnchorPane chatViewcontext;
    private Socket socket;
    private BufferedReader reader;
    private FileChooser fileChooser;
    private File filePath;
    PrintWriter writer;


    public void initialize() {

        String userName = LoginFormController.userName;    //lbl to set the user Name
        lblName.setText(String.valueOf(userName));
        try {
            socket = new Socket("localhost", 5001);
            System.out.println("Local Socket is connected with  Main Server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                String msg = reader.readLine();
                String[] tokens = msg.split(" ");   //Split can return 0 length  string array
                String cmd = tokens[0];    //Index 0 array

                StringBuilder completemsg = new StringBuilder();    //these method can  be reprsesent capacity in maseges and initial capacity is 16
                for (int i = 1; i < tokens.length; i++) {
                    completemsg.append(tokens[i] + " ");
                }

                String[] split1 = msg.split(" ");
                String st = " ";
                for (int i = 0; i < split1.length - 1; i++) {
                    st += split1[i + 1] + " ";
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

                    ImageView imageView = new ImageView(image);   //set Image Set
                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);


                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!cmd.equalsIgnoreCase(lblName.getText())) {

                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        Text text1 = new Text(" "+cmd+ " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);

                        Text text2 = new Text(": Me");

                        hBox.getChildren().add(text2);
                    }
                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));
                } else {

                    TextFlow tempflow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempflow.getChildren().add(txtName);
                    }
                    tempflow.getChildren().add(text);
                    tempflow.setMaxWidth(200);

                    TextFlow flow = new TextFlow(tempflow);
                    HBox hBox = new HBox(12);//

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {
                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(flow);
                    } else {
                        Text text2 = new Text(completemsg + ":Me");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(flow2);
                    }
                    Platform.runLater(() -> vBox.getChildren().add(hBox));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendOnAction(ActionEvent actionEvent) {
        sendMessage();


    }

    private void sendMessage() {   //button Send On Action
        String typemsg = txtmsgtype.getText();
       /* System.out.println("Msg Type :" + typemsg);*/
        writer.println(lblName.getText() + ": " + txtmsgtype.getText());
        txtmsgtype.clear();
        if (typemsg.equalsIgnoreCase("Bye")) {
            System.exit(0);
        }
    }

    public void imageSendOnMouseclicked(MouseEvent mouseEvent) {  //Image share
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(lblName.getText() + " " + "img" + filePath.getPath());

    }
}