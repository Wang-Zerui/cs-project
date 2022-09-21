package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.Personal.User;
import com.zerui.csproject.SplashScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    static Stage signUpStage;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    protected void login() throws IOException {
        if (User.login(email.getText(), password.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success");
            alert.showAndWait();
            Pane p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/zerui/csproject/menuView.fxml")));
            SplashScreen.getStage().setScene(new Scene(p));

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong email or password!");
            alert.show();
        }
    }

    @FXML
    protected void signUp() throws IOException {
        Pane p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/zerui/csproject/signUpView.fxml")));

        Scene scene = new Scene(p, 300, 500);
        signUpStage = new Stage();
        signUpStage.setScene(scene);
        signUpStage.setTitle("Hello!");
        signUpStage.setScene(scene);
        signUpStage.show();
    }

}