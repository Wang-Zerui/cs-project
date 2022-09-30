package com.zerui.csproject.Controller;

import com.google.cloud.firestore.SetOptions;
import com.zerui.csproject.Model.Personal.AccountModel;
import com.zerui.csproject.Model.Personal.User;
import com.zerui.csproject.Utils.DEF;
import com.zerui.csproject.Utils.Firebase;
import com.zerui.csproject.Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import com.zerui.csproject.Model.PostModel;

import java.io.*;
import java.time.Instant;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

public class CreatePostController {
    ArrayList<File> selFile = new ArrayList<>();
    @FXML
    TextField captionField;
    @FXML
    HBox imageScrollBox;
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label dragDropPrompt;
    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    private void initialize() {
        if (!DEF.showedCreatePostTip) {
            Utils.standard.addStyleSheet(new Alert(Alert.AlertType.INFORMATION, "Tutorial: Drag and drop images or click upload file to manually upload")).showAndWait();
            Utils.standard.addStyleSheet(new Alert(Alert.AlertType.INFORMATION, "Tutorial: Click on images to delete them")).showAndWait();
            DEF.showedCreatePostTip = true;
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
            addFile(f);
        }
    }

    @FXML
    private void createPost() throws IOException {
        if (captionField.getText().isEmpty()) Utils.standard.addStyleSheet(new Alert(Alert.AlertType.ERROR, "Please enter a caption!")).showAndWait();
        else if (selFile.isEmpty()) Utils.standard.addStyleSheet(new Alert(Alert.AlertType.ERROR, "Please upload images!")).showAndWait();
        else {
            Firebase.createPost(selFile, captionField.getText());
            Utils.standard.addStyleSheet(new Alert(Alert.AlertType.INFORMATION, "Post created successfully!")).showAndWait();
            MenuController.reload = true;
            MenuController.createPost.close();
        }
    }

    @FXML
    private void uploadFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpeg, *.jpg)", "*.png", "*.jpeg", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File f = fileChooser.showOpenDialog(MenuController.createPost);
        if (f!=null) addFile(f);

    }

    private void addFile(File f) throws FileNotFoundException {
        Image img = new Image(new FileInputStream(f));
        selFile.add(f);
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(250);
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
