package com.zerui.csproject.Model.Personal;


import java.util.ArrayList;
import java.util.UUID;

public class Account {
    public String name, username, about, uuid, profileLink;
    public ArrayList<String> postsArrayUid;

    public Account(String name, String username, String about, String uuid, String profileLink) {
        this.name = name;
        this.username = username;
        this.about = about;
        this.uuid = uuid;
        this.profileLink = profileLink;
    }

    public Account() {}
}
