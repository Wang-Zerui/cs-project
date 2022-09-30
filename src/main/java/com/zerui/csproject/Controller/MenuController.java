package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.Personal.User;
import com.zerui.csproject.Model.Post;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MenuController {
    public static Stage createPost;
    public static boolean reload = false;
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
    ArrayList<String> posts;
    int postCount = 0;
    @FXML
    protected void initialize() {
        init();
        scrollPane.vvalueProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                if(newValue.doubleValue() >= 0.95&&!progressIndicator.isVisible()) postLoader();
        });
        Platform.runLater(() -> profileView.setFill(new ImagePattern(new Image(User.getAccount().profileLink))));
    }

    protected void init() {
        System.out.println("init");
        progressIndicator.setVisible(false);
        postScroll.getChildren().clear();
        postScroll.getChildren().add(progressIndicator);
        postCount = 0;
        new Thread(() -> {
            try {
                posts = Firebase.loadPosts();
                Collections.reverse(posts);
                System.out.println("size:"+posts.size());
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            postLoader();
        }).start();
    }
    @FXML
    protected void createPost() throws IOException {
        Pane p = FXMLLoader.load(Utils.standard.fxmlPath("createPost.fxml"), Utils.getBundle());
        Scene scene = new Scene(p, 750, 400);
        createPost = new Stage();
        createPost.setMinWidth(750);
        createPost.setMinHeight(400);
        createPost.setMaxHeight(400);
        createPost.setMaxWidth(750);
        createPost.setScene(scene);
        createPost.setTitle("Create Post!");
        createPost.setScene(scene);
        createPost.setOnHidden(windowEvent -> {
            if (reload) {
                init();
                reload = false;
            }
        });
        createPost.show();
    }

    private Pane loadPost(Post post) throws IOException, ExecutionException, InterruptedException {
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
        username.setText(Firebase.getUsername(post.authorUid));
        postImageView.setImage(post.images.get(0));
        Date date = new Date(post.time*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d yyyy h:mm a", Locale.ENGLISH);
        String formattedDate = sdf.format(date);
        timestampLabel.setText(formattedDate);
        imageProfile.setFill(new ImagePattern(new Image(Firebase.getProfileURL(post.authorUid))));
        scrollRight.setOnAction(actionEvent -> {
            int index = post.images.indexOf(postImageView.getImage())+1;
            if (index>=post.images.size()) return;
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

//    private void changeMode() throws FileNotFoundException {
//        if (isDiscovery) {
//            explorePostImage.setImage(Utils.standard.loadImage("images/icons/Find-People.png"));
//            homeImage.setImage(Utils.standard.loadImage("images/icons/Home.png"));
//        } else {
//            explorePostImage.setImage(Utils.standard.loadImage("images/icons/Found-People.png"));
//            homeImage.setImage(Utils.standard.loadImage("images/icons/Home-Filled.png"));
//        }
//        isDiscovery = !isDiscovery;
//    }

    private void loadPost(String uid) {
        new Thread(()-> {
            if (progressIndicator.isVisible()) return;
            try {
                Platform.runLater(() -> progressIndicator.setVisible(true));
                Pane p = loadPost(getPost(uid));
                Platform.runLater(() -> postScroll.getChildren().add(postScroll.getChildren().size()-1, p));
                Platform.runLater(() -> progressIndicator.setVisible(false));
            } catch (IOException | InterruptedException | ExecutionException e) { throw new RuntimeException(e); }
        }).start();
    }

    private void postLoader() {
        System.out.println(postCount);
        if (postCount>=posts.size()) return;
        loadPost(posts.get(postCount));
        postCount++;
    }
}
