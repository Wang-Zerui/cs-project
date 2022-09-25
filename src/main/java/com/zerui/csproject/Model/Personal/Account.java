package com.zerui.csproject.Model.Personal;


import java.util.ArrayList;
import java.util.UUID;

public class Account {
    private String name, username, about, uuid, profileLink;
    private ArrayList<String> postsArrayUid;
    Account(String name, String username, String about, String uuid, String profileLink) {
        this.name = name;
        this.username = username;
        this.about = about;
        this.uuid = uuid;
        this.profileLink = profileLink;
    }
}
