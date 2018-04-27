package com.example.a0040.music;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by a0040 on 2018/3/22.
 */

public class Md5Util {



    public static String getMD5(String str) {
        try {
            // 生成一个 MD5 加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算 md5 函数
            md.update(str.getBytes());
            // digest() 最后确定返回 md5 hash 值，返回值为 8 为字符串。因为 md5 hash 值是 16 位的 hex 值，实际上就是 8 位的字符
            // BigInteger 函数则将 8 位的字符串转换成 16 位 hex 值，用字符串来表示；得到字符串形式的 hash 值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得 MD5 摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i <j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
