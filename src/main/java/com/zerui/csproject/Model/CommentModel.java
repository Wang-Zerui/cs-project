package com.zerui.csproject.Model;

import com.zerui.csproject.Model.Personal.Identifiable;

public class CommentModel extends Identifiable {
    public CommentModel(FirebaseCommentModel firebaseCommentModel) {
        super(firebaseCommentModel.authorID, firebaseCommentModel.message, firebaseCommentModel.commentID, firebaseCommentModel.timestamp);
    }
    public CommentModel(String authorID, String content, String id, long time) {
        super(authorID, content, id, time);
    }
}
