package com.zerui.csproject.Model;

public class FirebaseCommentModel {
    public String authorID, message, commentID;
    public long timestamp;
    public FirebaseCommentModel(String authorID, String message, String commentID, long timestamp) {
        this.authorID = authorID;
        this.message = message;
        this.commentID = commentID;
        this.timestamp = timestamp;
    }
    public FirebaseCommentModel() {}
}
