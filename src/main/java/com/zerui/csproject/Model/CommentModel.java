package com.zerui.csproject.Model;

public class CommentModel {
    public String authorID, message, commentID;
    public long timestamp;
    public CommentModel(String authorID, String message, String commentID, long timestamp) {
        this.authorID = authorID;
        this.message = message;
        this.commentID = commentID;
        this.timestamp = timestamp;
    }

    public CommentModel() {}
}
