package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class LoginFormController {
    public TextField txtloginName;
    public AnchorPane loginContext;
    static String userName;
    Scene scene;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtloginName.getText();
        if (userName.equals("")) {
            new Alert(Alert.AlertType.INFORMATION, "Login Failed,Please Enter Correct User Name").show();
        } else {
            Stage stage = (Stage) txtloginName.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ChatView.fxml"))));
            stage.centerOnScreen();
            stage.setTitle(userName);
            stage.show();


        }
    }
}
