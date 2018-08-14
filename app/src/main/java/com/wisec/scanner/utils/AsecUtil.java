package com.wisec.scanner.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by qwe on 2018/5/15.
 */

public class AsecUtil {
    private static final String KEY = "b0317c6340a04ae088ad26275f8a9260";

    private Boolean enabled;
    private Long timestamp;
    private String token;

    public void genToken(String identify) {
        String time = md5(md5(KEY + identify + String.valueOf(this.timestamp)) + identify + KEY);
        token = md5(md5(KEY + String.valueOf(enabled) + time) + KEY);
    }

    public boolean isEnable(String identify, Boolean enabled, Long timestamp, String token) {
        if (enabled == null || !enabled || timestamp == null || token == null) return false;
        final String key = "b0317c6340a04ae088ad26275f8a9260";
        String time = md5(md5(key + identify + String.valueOf(timestamp)) + identify + key);
        String token2 = md5(md5(key + String.valueOf(true) + time) + key);
        LogUtils.I("http", "token = " + token);
        LogUtils.I("http", "token2 = " + token2);
        return token.equals(token2);
    }

    public static String md5(String info) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2 将消息变成byte数组
            byte[] input = info.getBytes();
            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);
            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }
}
