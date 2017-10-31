package utils;

import android.util.Patterns;


public class ValidateUtil {
    public static boolean vlidatePhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty() || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        //TODO Complete passWord principle.
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            return false;
        }
        return true;
    }

}
