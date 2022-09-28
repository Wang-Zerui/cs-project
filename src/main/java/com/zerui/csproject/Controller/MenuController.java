package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.Personal.User;
import com.zerui.csproject.Model.Post;
import com.zerui.csproject.Model.PostModel;
import com.zerui.csproject.SplashScreen;
import com.zerui.csproject.Utils.Firebase;
import com.zerui.csproject.Utils.Utils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
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
    Circle profileView;
    @FXML
    ImageView homeImage, explorePostImage;
    boolean isDiscovery;
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {

        }
    });
    Thread loadPost = new Thread(()-> {
        if (progressIndicator.isVisible()) return;
        try {
            Platform.runLater(() -> progressIndicator.setVisible(true));
            Pane p = loadPost(getPost("6a2d062e-488d-40a2-9c26-63f84079060f"));
            Platform.runLater(() -> postScroll.getChildren().add(postScroll.getChildren().size()==1?0:postScroll.getChildren().size()-2, p)); // TODO FIX THIS
            Platform.runLater(() -> progressIndicator.setVisible(false));
        } catch (IOException e) { throw new RuntimeException(e); }
    });
    @FXML
    protected void initialize() {
        progressIndicator.setVisible(false);
        new Thread(loadPost).start();
        scrollPane.vvalueProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                if(newValue.doubleValue() >= 1.0) new Thread(loadPost).start();
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                profileView.setFill(new ImagePattern(new Image(User.getAccount().profileLink)));
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

    @FXML
    private void goHome() throws FileNotFoundException {
        changeMode();
    }

    @FXML
    protected void explorePosts() throws FileNotFoundException {
        changeMode();
    }

    private Pane loadPost(Post post) throws IOException {
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
        username.setText(post.author.username);
        postImageView.setImage(post.images.get(0));
        Date date = new Date(post.time*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d yyyy h:mm a", Locale.ENGLISH);
        String formattedDate = sdf.format(date);
        timestampLabel.setText(formattedDate);
        imageProfile.setFill(new ImagePattern(new Image(post.author.profileLink)));
        scrollRight.setOnAction(actionEvent -> {
            int index = post.images.indexOf(postImageView.getImage())+1;
            if (index<0||index>=post.images.size()) return;
            postImageView.setImage(post.images.get(index));
        });
        scrollLeft.setOnAction(actionEvent -> {
            int index = post.images.indexOf(postImageView.getImage())-1;
            if (index<0||index>=post.images.size()) return;
            postImageView.setImage(post.images.get(index));
        });
        return p;
    }

    private Post getPost(String uid) {
        return new Post(Firebase.getPost(uid));
    }

    private void changeMode() throws FileNotFoundException {
        if (isDiscovery) {
            explorePostImage.setImage(Utils.standard.loadImage("images/icons/Find-People.png"));
            homeImage.setImage(Utils.standard.loadImage("images/icons/Home.png"));
        } else {
            explorePostImage.setImage(Utils.standard.loadImage("images/icons/Found-People.png"));
            homeImage.setImage(Utils.standard.loadImage("images/icons/Home-Filled.png"));
        }
        isDiscovery = !isDiscovery;
    }
}
