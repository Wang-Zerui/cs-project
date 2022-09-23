package com.zerui.csproject.Controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.zerui.csproject.Utils.Firebase;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class SignUpController {
    File imageSel = new File(String.valueOf(getClass().getResource("src/main/resources/images/noprofile.png")));
    @FXML
    TextField name, username, email;
    @FXML
    PasswordField password, confirmPassword;
    @FXML
    ImageView profileView;

    @FXML
    protected void signUpPressed() throws IOException {
        if (username.getText().isEmpty()||password.getText().isEmpty()||name.getText().isEmpty()||email.getText().isEmpty()||confirmPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all blanks!").showAndWait();
        } else if (Firebase.userExists(username.getText())) {
            new Alert(Alert.AlertType.ERROR, "Username already exists!").showAndWait();
        } else if (!Objects.equals(password.getText(), confirmPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password not the same!").showAndWait();
        } else if (!EmailValidator.getInstance().isValid(email.getText())) {
            new Alert(Alert.AlertType.ERROR, "Email is invalid!").showAndWait();
        } else if (!Pattern.compile("^.{6,15}$").matcher(password.getText()).matches()) {
            new Alert(Alert.AlertType.ERROR, "Password must be between 6 to 10 characters!").showAndWait();
        } else {
            String uuid = Firebase.genUUID();
            URL url = Firebase.uploadFile(imageSel, "userprofile/"+uuid+".png");
            try {
                Firebase.createAccount(name.getText(), username.getText(), password.getText(), email.getText(), uuid, url);
                new Alert(Alert.AlertType.INFORMATION, "Success! Check your email for an email verification link!").showAndWait();
                LoginController.signUpStage.close();
            } catch (FirebaseAuthException e) {
                new Alert(Alert.AlertType.ERROR, "Email already taken!").showAndWait();
                System.out.println(e);
            }

        }
    }

    @FXML
    protected void changeImage() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpeg, *.jpg)", "*.png", "*.jpeg", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File f = fileChooser.showOpenDialog(LoginController.signUpStage);
        if (f!=null) {
            imageSel = f;
            profileView.setImage(new Image(imageSel.toURI().toString()));
            profileView.setFitHeight(100);
            profileView.setFitWidth(100);
            profileView.setPreserveRatio(true);
        }
    }

    @FXML
    protected void usernameChange() {
        username.setText(username.getText().replace(' ', '-'));
        username.positionCaret(username.getText().length());
    }

}