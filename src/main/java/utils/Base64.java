package utils;

import sun.misc.BASE64Decoder;

import java.io.IOException;

public class Base64 {
    public static String decoder(String data){
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = new byte[0];
        try {
            bytes = decoder.decodeBuffer(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("BASE64解密：" + new String(bytes));
        return new String(bytes);
    }
}
