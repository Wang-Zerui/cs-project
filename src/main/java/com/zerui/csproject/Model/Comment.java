package com.zerui.csproject.Model;

import com.zerui.csproject.Interface.Deletable;

public class Comment extends CommentModel implements Deletable {
    Comment(CommentModel c) {
        super(c.authorID, c.message, c.commentID, c.timestamp);
    }
    @Override
    public void delete() {

    }
}
