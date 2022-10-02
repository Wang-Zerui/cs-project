package com.zerui.csproject.Model.Personal;
import com.zerui.csproject.Utils.Firebase;

public class User {
    private static AccountModel accountModel;
    public static int login(String email, String password) {
        String id = Firebase.login(email, password);
        if (id!=null) {
            if (Firebase.isVerified(email)) {
                accountModel = Firebase.getAccount(id);
                System.out.println(accountModel);
                return 2;
            } else return 1;
        }
        else return 0;
    }
    public static void logout() {
        accountModel = null;
    }
    public static AccountModel getAccount() {
        return accountModel;
    }
}
