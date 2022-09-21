package com.zerui.csproject;

import com.zerui.csproject.Model.Utils.Firebase;
import com.zerui.csproject.Model.Utils.Mail;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

/**
 * Example of displaying a splash page for a standalone JavaFX application
 * modified from https://gist.github.com/jewelsea/2305098
 *
 */
public class SplashScreen extends Application {
    public static final String SPLASH_IMAGE ="file:splash.jpeg";

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private final Label progressText = new Label("Loading firebase...");
    private static Stage stage;

    public static void main(String[] args) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = new Stage();
            }
        });
        launch(args);
    }

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(SPLASH_IMAGE));
        loadProgress = new ProgressBar();
        splashLayout = new VBox();
        loadProgress.setMinWidth(800);
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                        "-fx-background-color: cornsilk; " +
                        "-fx-border-width:5; " +
                        "-fx-border-color: " +
                        "linear-gradient(" +
                        "to bottom, " +
                        "chocolate, " +
                        "derive(chocolate, 50%)" +
                        ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) {
        final Task initApp = new Task() {
            @Override
            protected Object call() throws Exception {
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
    }

    private void showMainStage() throws IOException {
        Pane p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginView.fxml")));
        Scene scene = new Scene(p);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
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
