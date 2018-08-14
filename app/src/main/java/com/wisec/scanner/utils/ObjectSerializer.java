package com.wisec.scanner.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerializer {

    public static String serialize(Serializable obj) {
        if (obj == null)
            return "";
        ByteArrayOutputStream serialObj = null;
        ObjectOutputStream objStream = null;
        try {
            serialObj = new ByteArrayOutputStream();
            objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return encodeBytes(serialObj.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (objStream != null) {
                try {
                    objStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serialObj != null) {
                try {
                    serialObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    public static Object deserialize(String str) {
        if (str == null || str.length() == 0)
            return null;
        ByteArrayInputStream serialObj = null;
        ObjectInputStream objStream = null;
        try {
            serialObj = new ByteArrayInputStream(decodeBytes(str));
            objStream = new ObjectInputStream(serialObj);
            return objStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (objStream != null) {
                try {
                    objStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serialObj != null) {
                try {
                    serialObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            char c = str.charAt(i);
            int p = i / 2;
            if (p < bytes.length) {
                bytes[p] = (byte) ((c - 'a') << 4);
                c = str.charAt(i + 1);
                bytes[p] += (c - 'a');
            }
        }
        return bytes;
    }

    private static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }
        return strBuf.toString();
    }
}
