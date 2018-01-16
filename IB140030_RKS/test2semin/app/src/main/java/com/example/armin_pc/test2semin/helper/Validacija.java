package com.example.armin_pc.test2semin.helper;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Armin_pc on 1.12.2017.
 */

public class Validacija {
    public static Boolean isValidEmail(String string){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static Boolean isValidPassword(String string, boolean allowSpecialChars){
        String PATTERN;
        if(allowSpecialChars){
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
            PATTERN = "^[a-zA-Z@#$%]\\w{5,19}$";
        }else{
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
            PATTERN = "^[a-zA-Z]\\w{5,19}$";
        }



        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static Boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public static Boolean isNumeric(String string){
        return TextUtils.isDigitsOnly(string);
    }




}
