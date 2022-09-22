package com.zerui.csproject.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.impl.*;
import java.io.IOException;
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

    public static void main(String[] args) {
        System.out.println(path);
    }
}
