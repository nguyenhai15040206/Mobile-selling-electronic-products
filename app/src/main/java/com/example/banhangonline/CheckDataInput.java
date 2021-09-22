package com.example.banhangonline;

import android.util.Patterns;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckDataInput {


    // kiểm tra số điện thoại hợp lệ
    public static boolean isPhoneNumber(String input)
    {
        if(isRegex(input) && input.length() == 10 && input.charAt(0) == '0')
        {
            return true;
        }
        return false;
    }

    public static boolean isRegex(String input)
    {
        for (char c: input.toCharArray())
        {
            if(c < '0' || c >'9')
            {
                return false;
            }
        }
        return  true;
    }

    public static  boolean isTextContainSpace(String input)
    {
        for (int i=0 ; i < input.length(); i++)
        {
            if(input.charAt(i) == ' '){
                return false;
            }
        }
        return true;
    }

    public  static  boolean isEmail(String input)
    {
        if(Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            return  true;
        }
        return false;
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertByteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
