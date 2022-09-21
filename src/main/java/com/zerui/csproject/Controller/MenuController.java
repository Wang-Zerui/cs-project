package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.io.IOException;

public class MenuController {
    @FXML
    VBox postScroll;

    @FXML
    protected void initialize() throws IOException {
        for (int i = 0; i < 10; i ++) postScroll.getChildren().add(Utils.loadPane("/com/zerui/csproject/userPost.fxml"));
    }


}
