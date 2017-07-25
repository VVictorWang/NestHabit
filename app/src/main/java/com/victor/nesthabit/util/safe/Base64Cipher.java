package com.victor.nesthabit.util.safe;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class Base64Cipher {

    public Base64Cipher() {
    }


    public static byte[] decrypt(byte[] res) {
        return Base64.decode(res, Base64.DEFAULT);
    }

    public static byte[] encrypt(byte[] res) {
        return Base64.encode(res, Base64.DEFAULT);
    }

    public static String encrypt(String res) {
        try {
            byte[] temp = objectToByte(res);
            byte[] result = encrypt(temp);
            return HexUtils.encodeHexStr(result, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象 转为 byte[]
     *
     * @param obj
     * @return
     */
    public static byte[] objectToByte(Object obj) throws Exception {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } finally {
            if (oos != null) oos.close();
        }
    }


}
