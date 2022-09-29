package com.zerui.csproject;

import com.zerui.csproject.Utils.Firebase;
import com.zerui.csproject.Utils.Mail;
import com.zerui.csproject.Utils.Utils;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class SplashScreen extends Application {
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws FileNotFoundException {
        ImageView splash = new ImageView(new Image(getClass().getResourceAsStream("images/splash.png")));
        loadProgress = new ProgressBar();
        splashLayout = new VBox();
        loadProgress.setMinWidth(270);
        splash.setFitWidth(270);
        splash.setFitHeight(270);
        splash.setPreserveRatio(true);
        splashLayout.getChildren().addAll(splash, loadProgress);
        splashLayout.setStyle("-fx-border-color: black;-fx-border-width:5;");
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) {
        final Task initApp = new Task() {
            @Override
            protected Object call() throws InterruptedException, IOException, URISyntaxException {
                Firebase.initFirebase();
                Mail.initMail();
                for (int i = 0; i < 2000; i++) {
                    Thread.sleep(0);
                    updateProgress(i + 1, 2000);
                }
                Thread.sleep(800);
                return null;
            }
        };
        showSplash(
                initStage,
                initApp,
                this::showMainStage
        );
        new Thread(initApp).start();
        String s = "11";
        s.charAt(1);
        boolean a = s=="1";
    }

    private void showMainStage() throws IOException {
        Pane p = FXMLLoader.load(Utils.standard.fxmlPath("loginView.fxml"));
        Scene scene = new Scene(p);
        stage =  new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMinHeight(380);
        stage.setMinWidth(250);
        stage.show();
    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
                try {
                    initCompletionHandler.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        initStage.setScene(splashScene);
        initStage.show();
    }

    public interface InitCompletionHandler {
        void complete() throws IOException;
    }

    public static Stage getStage() {
        return stage;
    }
}
