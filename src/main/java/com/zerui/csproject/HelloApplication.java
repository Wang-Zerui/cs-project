package com.zerui.csproject;

import com.google.firebase.auth.FirebaseAuthException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 280, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, FirebaseAuthException {
//        launch();
//        Firebase.initFirebase();
//        FirebaseAuth a = FirebaseAuth.getInstance(FirebaseApp.getInstance());
//        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
//                .setEmail("h2010155@nushigh.edu.sg")
//                .setPassword("Hello World")
//                .setEmailVerified(false);
//        a.createUser(createRequest);
//        System.out.println(a.generateEmailVerificationLink("h2010155@nushigh.edu.sg"));
//        generateEmailVerificationLink("h2010155@nushigh.edu.sg")
//        System.out.println(a.getUserByEmail("h2010155@nushigh.edu.sg").isEmailVerified());
//        WebManager.sendPOST("h2010155@nushigh.edu.sg", "Student01");
    }
}