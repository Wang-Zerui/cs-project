package com.zerui.csproject.Model;

import com.zerui.csproject.Interface.Deletable;

public class Comment extends CommentModel implements Deletable {
    public Comment(CommentModel c) {
        super(c.authorID, c.content, c.id, c.time);
    }
    @Override
    public void delete() {

    }
}
