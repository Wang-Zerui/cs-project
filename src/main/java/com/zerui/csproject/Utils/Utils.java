package com.zerui.csproject.Utils;

import com.zerui.csproject.SplashScreen;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.impl.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static com.zerui.csproject.Utils.DEF.*;

public class Utils {
    static final AppDirs appDirs = getInstance();
    static final String path = appDirs.getUserDataDir(CREDENTIALS_APP_NAME, CREDENTIALS_VERSION, CREDENTIALS_AUTHOR);
    public static Pane loadPane(String path) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource(path)));
    }
    public static AppDirs getInstance() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("mac os x")) return new MacOSXAppDirs();
        else if (os.startsWith("windows")) return new WindowsAppDirs(new ShellFolderResolver());
        else return new UnixAppDirs();
    }
    public static Alert addStyleSheet(Alert alert) {
        alert.getDialogPane().getStylesheets().add(DEF.dialogCss);
        return alert;
    }
    public static URL fxmlPath(String name) {
        return SplashScreen.class.getResource("fxml/"+name);
    }
    public static void main(String[] args) {
        System.out.println(path);
    }
}
