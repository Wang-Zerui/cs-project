package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.PostModel;
import com.zerui.csproject.SplashScreen;
import com.zerui.csproject.Utils.Firebase;
import com.zerui.csproject.Utils.Utils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MenuController {
    public static Stage createPost;
    @FXML
    VBox postScroll;
    @FXML
    ScrollPane scrollPane;
    @FXML
    ProgressIndicator progressIndicator;
    @FXML
    protected void initialize() {
        progressIndicator.setVisible(false);
        Thread loadPost = new Thread(()-> {
            if (progressIndicator.isVisible()) return;
            try {
                Platform.runLater(() -> progressIndicator.setVisible(true));
                Pane p = loadPost(getPost("8585457d-700e-46b9-9a8c-438b52bfd21a"));
                Platform.runLater(() -> postScroll.getChildren().add(postScroll.getChildren().size()==1?0:postScroll.getChildren().size()-2, p));
                Platform.runLater(() -> progressIndicator.setVisible(false));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }});
        new Thread(loadPost).start();
        scrollPane.vvalueProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                if(newValue.doubleValue() >= 1.0) {
                    new Thread(loadPost).start();
                }
        });
    }

    @FXML
    protected void createPost() throws IOException {
        Pane p = FXMLLoader.load(Utils.standard.fxmlPath("createPost.fxml"));
        Scene scene = new Scene(p, 750, 400);
        createPost = new Stage();
        createPost.setMinWidth(750);
        createPost.setMinHeight(400);
        createPost.setMaxHeight(400);
        createPost.setMaxWidth(750);
        createPost.setScene(scene);
        createPost.setTitle("Create Post!");
        createPost.setScene(scene);
        createPost.show();
    }

    private Pane loadPost(PostModel postModel) throws IOException {
        VBox p = Utils.standard.loadPane("fxml/userPost.fxml");
        Label username = (Label) p.lookup("#username");
        Label timestampLabel = (Label) p.lookup("#timestampLabel");
        Label likeCount = (Label) p.lookup("#likeCount");
        Button scrollLeft = (Button) p.lookup("#scrollLeft");
        Button scrollRight = (Button) p.lookup("#scrollRight");
        Button postComment = (Button) p.lookup("#postComment");
        Circle imageProfile = (Circle) p.lookup("#imageProfile");
        TextField commentField = (TextField) p.lookup("#commentField");
        ImageView postImageView = (ImageView) p.lookup("#postImageView");
        ImageView viewComments = (ImageView) p.lookup("#viewComments");
        ImageView like = (ImageView) p.lookup("#like");
        username.setText("I hate my life");
        scrollLeft.setVisible(false);
        scrollRight.setVisible(false);
        ArrayList<Image> images = new ArrayList<>();
        for (String i:postModel.imagePosts) {
            Image image = new Image(i);
            images.add(image);
        }
        postImageView.setImage(images.get(0));
        if (images.size()>1) scrollRight.setVisible(true);
        Date date = new Date(postModel.time*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d yyyy h:mm a", Locale.ENGLISH);
        String formattedDate = sdf.format(date);
        timestampLabel.setText(formattedDate);
        return p;
    }

    private PostModel getPost(String uid) {
        return Firebase.getPost(uid);
    }

}
