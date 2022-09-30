package com.zerui.csproject.Utils;

import com.zerui.csproject.SplashScreen;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.impl.*;

import java.awt.geom.RectangularShape;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.zerui.csproject.Utils.DEF.*;

public class Utils {
    public final static Utils standard = new Utils();

    final AppDirs appDirs = getInstance();
    final String path = appDirs.getUserDataDir(CREDENTIALS_APP_NAME, CREDENTIALS_VERSION, CREDENTIALS_AUTHOR);
    public <T> T loadPane(String path) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(SplashScreen.class.getResource(path)));
    }
    public AppDirs getInstance() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("mac os x")) return new MacOSXAppDirs();
        else if (os.startsWith("windows")) return new WindowsAppDirs(new ShellFolderResolver());
        else return new UnixAppDirs();
    }
    public Alert addStyleSheet(Alert alert) {
        alert.getDialogPane().getStylesheets().add(DEF.dialogCss);
        return alert;
    }
    public URL fxmlPath(String name) {
        return SplashScreen.class.getResource("fxml/"+name);
    }
    public void main(String[] args) {
        System.out.println(path);
    }
    public static ResourceBundle getBundle() {
        return isCN? resources_CN:resources_EN;
    }
}
