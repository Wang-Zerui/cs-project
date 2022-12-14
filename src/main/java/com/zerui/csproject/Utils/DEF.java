package com.zerui.csproject.Utils;

import com.zerui.csproject.SplashScreen;
import javafx.scene.image.Image;

import java.util.Locale;
import java.util.ResourceBundle;

public class DEF {
    public static final String apiURL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=";
    public static final String webKey = "AIzaSyAQpKFN9I8TJMwRwutHgdMSRuOb4cbTzew";
    public static final String CREDENTIALS_APP_NAME = "csProject";
    public static final String CREDENTIALS_AUTHOR = "iamnumber4";
    public static final String CREDENTIALS_VERSION = "v1.0";
    public static final String dialogCss = SplashScreen.class.getResource("css/dialog.css").toExternalForm();
    public static boolean showedCreatePostTip = false;
    public static boolean isCN = false;
    public static ResourceBundle resources_CN = ResourceBundle.getBundle("locales.strings", new Locale("cn"));
    public static ResourceBundle resources_EN = ResourceBundle.getBundle("locales.strings", new Locale("en"));
    public static final Image unlikeImage = new Image(SplashScreen.class.getResourceAsStream("images/icons/Unlike.png"));
    public static final Image likeImage = new Image(SplashScreen.class.getResourceAsStream("images/icons/Like.png"));
}
