package com.zerui.csproject.Model.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class Utils {
    public static Pane loadPane(String path) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource(path)));
    }
}
