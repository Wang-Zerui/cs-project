package com.zerui.csproject.Controller;

import com.zerui.csproject.Model.Personal.Account;
import com.zerui.csproject.Model.Personal.User;
import com.zerui.csproject.Utils.DEF;
import com.zerui.csproject.Utils.Firebase;
import com.zerui.csproject.Utils.Utils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import com.zerui.csproject.Model.PostModel;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.ArrayUtils;

public class CreatePostController {
    ArrayList<File> selFile = new ArrayList<>();
    @FXML
    TextField captionField;
    @FXML
    HBox imageScrollBox;
    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    private void initialize() {

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
    private void createPost() throws IOException {
        if (captionField.getText().isEmpty()) Utils.standard.addStyleSheet(new Alert(Alert.AlertType.ERROR, "Please enter a caption!")).showAndWait();
        else if (selFile.isEmpty()) Utils.standard.addStyleSheet(new Alert(Alert.AlertType.ERROR, "Please upload images!")).showAndWait();
        Account currUser = User.getAccount();
        String postID = Firebase.genUUID();
        ArrayList<String> imageLinks = new ArrayList<>();
        for (File file:selFile) {
            imageLinks.add(Firebase.uploadFile(file, String.format("posts/%s/%s", postID ,file.getName())).toString());
        }
        PostModel model = new PostModel(postID, currUser.getUuid(), captionField.getText(), imageLinks, Instant.now().getEpochSecond());
    }
}
