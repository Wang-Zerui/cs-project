package com.zerui.csproject.Model;

import com.zerui.csproject.Interface.Deletable;
import com.zerui.csproject.Model.Personal.Account;
import com.zerui.csproject.Model.Personal.AccountModel;
import com.zerui.csproject.Model.PostModel;
import com.zerui.csproject.Utils.Firebase;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Post extends PostModel implements Deletable {
    public ArrayList<Comment> comments = new ArrayList<>();
    public ArrayList<Image> images = new ArrayList<>();

    public Post(PostModel p) {
        super(p);
        loadImages();
        loadComments();
    }

    private void loadImages() {
        for (String i:super.imagePosts) {
            Image image = new Image(i);
            images.add(image);
        }
    }

    private void loadComments() {
        for (CommentModel i:Firebase.getComments(super.id)) {
            comments.add(new Comment(i));
        }
    }

    @Override
    public void delete() {
        // TODO Implement delete
    }
}
