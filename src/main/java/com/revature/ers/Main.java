package com.revature.ers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String ex1 = "Hello";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(ex1.getBytes());
        byte[] digest = messageDigest.digest();
        StringBuffer hexStr=new StringBuffer();
        for (int i = 0; i< digest.length; i++){
            hexStr.append(Integer.toHexString(0xFF & digest[i]));
        }
        System.out.println(hexStr);
    }
}
