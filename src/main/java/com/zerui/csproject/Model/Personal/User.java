package com.zerui.csproject.Model.Personal;
import com.zerui.csproject.Utils.Firebase;

public class User extends Account {
    private static boolean isLogin;
    private static String username, password, hash, email;
    public static int login(String email, String password) {
        String usrNm = Firebase.login(email, password);
        if (usrNm!=null) {
            if (Firebase.isVerified(email)) {
                User.isLogin = true;
                User.username = usrNm;
                User.password = password;
                User.hash = Firebase.genHash(username, password);
                User.email = email;
                return 2;
            } else return 1;
        }
        else return 0;
    }
}
