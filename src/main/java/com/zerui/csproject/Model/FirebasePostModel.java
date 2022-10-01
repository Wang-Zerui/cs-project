package com.zerui.csproject.Model;

import com.zerui.csproject.Utils.Firebase;

import java.util.ArrayList;

public class FirebasePostModel {
    public String uid, authorUid, caption;
    public ArrayList<String> imagePosts = new ArrayList<>(), likeUid = new ArrayList<>();

    public long time;
    public FirebasePostModel(String uid, String authorUid, String caption, ArrayList<String> imagePosts, long time) {
        this.uid = uid;
        this.authorUid = authorUid;
        this.caption = caption;
        this.time = time;
        this.imagePosts = imagePosts;
        this.likeUid = new ArrayList<>();
    }
    public FirebasePostModel() {}
}
