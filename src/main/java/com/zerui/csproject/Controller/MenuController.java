package com.zerui.csproject.Controller;

import com.zerui.csproject.Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuController {
    @FXML
    VBox postScroll;
    @FXML
    ScrollPane scrollPane;
    @FXML
    ProgressIndicator loadPosts;
    @FXML
    protected void initialize() throws IOException {
        loadPosts.setVisible(false);
        for (int i = 0; i < 10; i ++) postScroll.getChildren().add(Utils.loadPane("/com/zerui/csproject/userPost.fxml"));
    }

}
