package com.zerui.csproject.Model;

import java.util.ArrayList;

public class PostModel {
    String uid, authorUid, caption;
    ArrayList<String> comments;
    ArrayList<String> imagePosts;
    int time;

    PostModel(String uid, String authorUid, String caption, ArrayList<String> comments, ArrayList<String> imagePosts, int time) {
        this.uid = uid;
        this.authorUid = authorUid;
        this.caption = caption;
        this.comments = comments;
        this.imagePosts = imagePosts;
        this.time = time;
    }

    PostModel() {}
}
