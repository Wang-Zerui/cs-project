package com.zerui.csproject.Model.Personal;
import com.zerui.csproject.Model.Utils.Firebase;

public class User extends Account {
    private static boolean isLogin;
    private static String username, password, hash, email;
    public static boolean login(String email, String password) {
        String usrNm = Firebase.login(email, password);
        if (usrNm!=null) {
            User.isLogin = true;
            User.username = usrNm;
            User.password = password;
            User.hash = Firebase.genHash(username, password);
            User.email = email;
            return true;
        }
        else return false;
    }
}
