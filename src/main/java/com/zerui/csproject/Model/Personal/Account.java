package com.zerui.csproject.Model.Personal;

import com.zerui.csproject.Model.Post;
import com.zerui.csproject.Utils.Firebase;

import java.util.ArrayList;

public class Account extends AccountModel {
    ArrayList<Post> posts = new ArrayList<>();
    public Account(String uid) {
        super(Firebase.getAccount(uid));
        if (super.postsArrayUid!=null) {
            for (String i:super.postsArrayUid) {
                posts.add(new Post(Firebase.getPost(i)));
            }
        }
    }

}
