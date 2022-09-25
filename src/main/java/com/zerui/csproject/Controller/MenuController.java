package com.zerui.csproject.Controller;

import com.zerui.csproject.SplashScreen;
import com.zerui.csproject.Utils.Utils;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MenuController {
    public static Stage createPost;
    @FXML
    VBox postScroll;
    @FXML
    ScrollPane scrollPane;
    @FXML
    ProgressIndicator loadPosts;
    @FXML
    protected void initialize() throws IOException {
        loadPosts.setVisible(false);
        scrollPane.vvalueProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                if(newValue.doubleValue() >= 0.95){
                    System.out.println( "AT TOP" );
                    for (int i = 0; i < 10; i++) {
                        try {
                            postScroll.getChildren().add(Utils.standard.loadPane("/com/zerui/csproject/fxml/userPost.fxml"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        });
        for (int i = 0; i < 10; i++) postScroll.getChildren().add(Utils.standard.loadPane("/com/zerui/csproject/fxml/userPost.fxml"));
    }

    @FXML
    protected void createPost() throws IOException {
        Pane p = FXMLLoader.load(Utils.standard.fxmlPath("createPost.fxml"));
        Scene scene = new Scene(p, 750, 380);
        createPost = new Stage();
        createPost.setMinWidth(750);
        createPost.setMinHeight(380);
        createPost.setMaxHeight(380);
        createPost.setMaxWidth(750);
        createPost.setScene(scene);
        createPost.setTitle("Create Post!");
        createPost.setScene(scene);
        createPost.show();
    }

    private Pane loadPost(ArrayList<String> imageURLs) throws IOException {
        VBox p = Utils.standard.loadPane("fxml/userPost.fxml");
        p.lookup("");
        ArrayList<Image> images = new ArrayList<>();
        for (String i:imageURLs) {
            Image image = new Image(i);
            images.add(image);
        }
        return null;
    }

}
