package com.zerui.csproject.Model;

import java.util.ArrayList;

public class PostModel {
    String uid, authorUid, caption;
    ArrayList<String> imagePosts;
    long time;

    public PostModel(String uid, String authorUid, String caption, ArrayList<String> imagePosts, long time) {
        this.uid = uid;
        this.authorUid = authorUid;
        this.caption = caption;
        this.imagePosts = imagePosts;
        this.time = time;
    }

    public PostModel() {}
}
