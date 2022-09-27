package com.zerui.csproject.Model.Personal;


import java.util.ArrayList;

public class AccountModel {
    public String name, username, about, uuid, profileLink;
    public ArrayList<String> postsArrayUid = new ArrayList<>();

    public AccountModel(String name, String username, String about, String uuid, String profileLink) {
        this.name = name;
        this.username = username;
        this.about = about;
        this.uuid = uuid;
        this.profileLink = profileLink;
    }

    public AccountModel() {}

    public AccountModel(AccountModel accountModel) {
        this.name = accountModel.name;
        this.username = accountModel.username;
        this.about = accountModel.about;
        this.uuid = accountModel.uuid;
        this.profileLink = accountModel.profileLink;
        this.postsArrayUid = accountModel.postsArrayUid;
    }
}
