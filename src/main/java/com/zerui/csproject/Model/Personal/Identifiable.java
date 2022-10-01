package com.zerui.csproject.Model.Personal;

public abstract class Identifiable {
    public String authorID, content, id;
    public long time;
    public Identifiable(String authorID, String content, String id, long time) {
        this.authorID = authorID;
        this.content = content;
        this.id = id;
        this.time = time;
    }
    public Identifiable() {}

}
