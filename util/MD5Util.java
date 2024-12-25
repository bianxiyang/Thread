package cn.com.tcsl.fast.kds.server.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private MD5Util() {
        throw new RuntimeException();
    }

    public static String md5(String src) {
        return md5(src, "");
    }

    public static String md5(String src, String key) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException var10) {
            throw new RuntimeException(var10);
        }

        md5.reset();
        byte[] temp;

        try {
            md5.update(src.getBytes("UTF8"));
            temp = md5.digest(key.getBytes("UTF8"));
        } catch (UnsupportedEncodingException var9) {
            throw new RuntimeException(var9);
        }

        StringBuilder result = new StringBuilder(32);
        byte[] var5 = temp;
        int var6 = temp.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            byte aTemp = var5[var7];
            result.append(Integer.toHexString(255 & aTemp | -256).substring(6));
        }

        return result.toString();
    }

}
