package com.zerui.csproject.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PostModel {
    public String uid, authorUid, caption;
    public ArrayList<String> imagePosts;
    public long time;

    public PostModel(String uid, String authorUid, String caption, ArrayList<String> imagePosts, long time) {
        this.uid = uid;
        this.authorUid = authorUid;
        this.caption = caption;
        this.imagePosts = imagePosts;
        this.time = time;
    }

    public PostModel() {}

    public PostModel(PostModel p) {
        this.uid = p.uid;
        this.authorUid = p.authorUid;
        this.caption = p.caption;
        this.imagePosts = p.imagePosts;
        this.time = p.time;
    }
}
