package com.zerui.csproject.Model;

import com.zerui.csproject.Model.Personal.Identifiable;

import java.util.ArrayList;

public class PostModel extends Identifiable {
    public ArrayList<String> imagePosts = new ArrayList<>(), likeUid = new ArrayList<>();

    public PostModel(FirebasePostModel firebasePostModel) {
        super(firebasePostModel.authorUid, firebasePostModel.caption, firebasePostModel.uid, firebasePostModel.time);
        this.imagePosts = firebasePostModel.imagePosts;
        this.likeUid = firebasePostModel.likeUid;
        System.out.println(likeUid);
    }

    public PostModel() {super();}

    public PostModel(PostModel p) {
        super(p.authorID, p.content, p.id, p.time);
        this.imagePosts = p.imagePosts;
        this.likeUid = p.likeUid;
    }
}
