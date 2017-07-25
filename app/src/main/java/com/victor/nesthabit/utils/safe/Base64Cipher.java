package com.victor.nesthabit.utils.safe;

import android.util.Base64;

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
            byte[] temp = ByteUtils.objectToByte(res);
            byte[] result = encrypt(temp);
            return HexUtils.encodeHexStr(result, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
