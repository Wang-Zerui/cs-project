package com.zerui.csproject.Controller;

import com.zerui.csproject.Utils.DEF;
import com.zerui.csproject.Utils.Utils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.ArrayUtils;

public class CreatePostController {
    ArrayList<File> selFile = new ArrayList<>();
    @FXML
    HBox imageScrollBox;
    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
            // write code that prints hello world
//            FilenameUtils.getExtension();
        }
    }

    @FXML
    private void handleDropped(DragEvent event) throws FileNotFoundException {
        String[] validExt = {"jpg", "jpeg", "png"};
        List<File> files = event.getDragboard().getFiles();
        for (File f:files) {
            if (!ArrayUtils.contains(validExt, FilenameUtils.getExtension(f.getName()))) {
                Utils.standard.addStyleSheet(new Alert(Alert.AlertType.ERROR, "Invalid file type!")).showAndWait();
                continue;
            }
            Image img = new Image(new FileInputStream(f));
            selFile.add(f);
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);
            imageView.setImage(img);
            imageView.setOnMouseClicked(mouseEvent -> {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Delete image?");
                a.getDialogPane().getStylesheets().add(DEF.dialogCss);
                Optional<ButtonType> result = a.showAndWait();
                if (result.isPresent()) if (result.get()==ButtonType.OK) {
                    selFile.remove(imageScrollBox.getChildren().indexOf(imageView));
                    imageScrollBox.getChildren().remove(imageView);
                }
            });
            imageScrollBox.getChildren().add(imageView);
        }
    }

    @FXML
    private void createPost() {
        
    }
}
