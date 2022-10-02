package com.zerui.csproject;

import com.google.firebase.auth.FirebaseAuthException;
import com.zerui.csproject.Utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Formatter;
import java.util.regex.Matcher;

public class HelloApplication extends Application {
    public void start(Stage stage) {
        Pane p = new Pane();
        Scene scene = new Scene(p, 750, 380);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        Utils.standard.addStyleSheet(new Alert(Alert.AlertType.ERROR, "Password must be between 6 to 15 characters!")).showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}