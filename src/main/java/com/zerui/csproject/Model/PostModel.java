package com.zerui.csproject.Model;

import com.zerui.csproject.Model.Personal.Identifiable;
import com.zerui.csproject.Utils.Firebase;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PostModel extends Identifiable {
    public ArrayList<String> imagePosts = new ArrayList<>(), likeUid = new ArrayList<>();
    public ArrayList<File> selFile;
    public PostModel(FirebasePostModel firebasePostModel) {
        super(firebasePostModel.authorUid, firebasePostModel.caption, firebasePostModel.uid, firebasePostModel.time);
        this.imagePosts = firebasePostModel.imagePosts;
        this.likeUid = firebasePostModel.likeUid;
        System.out.println(likeUid);
    }

    public PostModel(ArrayList<File> selFile, String caption) {
        this.selFile = selFile;
        this.content = caption;
    }

    public PostModel() {super();}

    public PostModel(PostModel p) {
        super(p.authorID, p.content, p.id, p.time);
        this.imagePosts = p.imagePosts;
        this.likeUid = p.likeUid;
    }
    @Override
    public void create() {
        try {
            Firebase.createPost(selFile, content);
        } catch (Exception ignored) {}
    }
}
