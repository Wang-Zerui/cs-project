package com.zerui.csproject.Model;

import com.zerui.csproject.Model.Personal.Identifiable;
import com.zerui.csproject.Utils.Firebase;

public class CommentModel extends Identifiable {
    String postID;
    public CommentModel(FirebaseCommentModel firebaseCommentModel) {
        super(firebaseCommentModel.authorID, firebaseCommentModel.message, firebaseCommentModel.commentID, firebaseCommentModel.timestamp);
    }
    public CommentModel(String authorID, String content, String id, long time, String postID) {
        super(authorID, content, id, time);
        this.postID = postID;
    }
    @Override
    public void create() {
        Firebase.createComment(authorID, content, id, time, postID);
    }
}
