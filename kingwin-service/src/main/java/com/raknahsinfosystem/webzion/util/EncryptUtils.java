package com.raknahsinfosystem.webzion.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class EncryptUtils {
	private static final String DEFAULT_ENCODING = "UTF-8"; 
    static BASE64Encoder enc = new BASE64Encoder();
    static BASE64Decoder dec = new BASE64Decoder();
    static String key = "shank";

    public static String base64encode(String text) {
        try {
            return enc.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }//base64encode

    public static String base64decode(String text) {
        try {
            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }//base64decode

    public static void main(String[] args) {
        String en=getEncryptedMesssage("{user : \"ztony\"}");
        System.out.println(en);
        System.out.println(getDecryptedMesssage(en));
        
    }
    public static String getEncryptedMesssage(String jsonString){
    	String xorMessage=xorMessage(jsonString, key);
    	String xor_base64 = base64encode(xorMessage);
    	return xor_base64;
    }
    public static String getDecryptedMesssage(String xor_base64){
    	String xorMessage=base64decode(xor_base64);
    	String jsonString = xorMessage(xorMessage,key);
    	return jsonString;
    }

    public static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char)(mesg[i] ^ keys[i % kl]);
            }//for i

            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }//xorMessage
}