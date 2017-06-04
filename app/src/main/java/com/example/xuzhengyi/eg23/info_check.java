package com.example.xuzhengyi.eg23;

/**
 * Created by XUZhengyi on 2017/6/3.
 */

public class info_check {
   public boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
