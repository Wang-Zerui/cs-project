package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.Comment;
import com.zerui.csproject.Model.CommentModel;
import com.zerui.csproject.Utils.Firebase;
import com.zerui.csproject.Utils.StringHolder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ViewCommentController {
    @FXML
    VBox commentVbox;
    @FXML
    protected void initialize() {
        String postID = StringHolder.getInstance().getString();
        new Thread(() -> {
            ArrayList<Comment> comments = getComments(postID);
            populate(comments);
        }).start();
    }
    private void populate(ArrayList<Comment> comments) {
        Platform.runLater(() -> {
            if (comments.isEmpty()) commentVbox.getChildren().add(new Label("No comments"));
        });
        for (int i = 0; i < comments.size(); i ++) {
            Comment comment = comments.get(i);
            String username = Firebase.getUsername(comment.authorID);
            int finalI = i;
            Platform.runLater(() -> {
                Text commentLabel = new Text(String.format("%s: %s", username , comment.content));
                commentLabel.setWrappingWidth(290);
                commentLabel.setTextAlignment(TextAlignment.JUSTIFY);
                commentVbox.getChildren().add(commentLabel);
                if (finalI!=comments.size()-1) commentVbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
            });
        }
    }
    private ArrayList<Comment> getComments(String postID) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (CommentModel commentModel:Firebase.getComments(postID)) {
            comments.add(new Comment(commentModel));
        }
        return comments;
    }
}

