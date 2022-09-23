package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.Personal.User;
import com.zerui.csproject.Utils.Firebase;
import com.zerui.csproject.SplashScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class LoginController {
    static Stage signUpStage;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    protected void login() throws IOException {
        int status = User.login(email.getText(), password.getText());
        if (status==2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success");
            alert.showAndWait();
            Pane p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/zerui/csproject/menuView.fxml")));
            SplashScreen.getStage().setScene(new Scene(p));
        } else if (status == 1) {
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType resendEmail = new ButtonType("Resend Email", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please verify your email!", ok, resendEmail);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(ok)==resendEmail) {
                Firebase.sendVerificationEmail(email.getText());
                new Alert(Alert.AlertType.INFORMATION, "Sent email!").showAndWait();
            }
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
    @FXML
    protected void resetPassword() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reset password");
        dialog.setHeaderText("Enter details");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(email -> {
            if (EmailValidator.getInstance().isValid(email))
            if (Firebase.resetPassword(email)) new Alert(Alert.AlertType.INFORMATION, "Email verification link sent!").showAndWait();
            else new Alert(Alert.AlertType.ERROR, "Email does not exist!").showAndWait();
            else new Alert(Alert.AlertType.ERROR, "Invalid email!").showAndWait();
        });
    }

}