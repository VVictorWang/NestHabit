package com.victor.nesthabit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.victor.nesthabit.utils.safe.Base64Cipher;
import com.victor.nesthabit.utils.safe.ByteUtils;
import com.victor.nesthabit.utils.safe.Cipher;
import com.victor.nesthabit.utils.safe.HexUtils;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class PrefsUtils {
    private static String PREF_HIGH_QUALITY = "pref_high_quality";

    private SharedPreferences sp;
    public static final String KEY_PK_HOME = "msg_pk_home";
    public static final String KEY_PK_NEW = "msg_pk_new";

    public PrefsUtils(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * *************** get ******************
     */

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public Object get(String key) {
        return get(key, (Cipher) null);
    }

    public Object get(String key, Cipher cipher) {
        try {
            String hex = getString(key, (String) null);
            if (hex == null)
                return null;
            byte[] bytes = HexUtils.decodeHex(hex.toCharArray());
            if (cipher != null)
                bytes = cipher.decrypt(bytes);
            Object obj = ByteUtils.byteToObject(bytes);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * *************** put ******************
     */
    public void put(String key, Object ser) {
        put(key, ser, null);
        put("","", new Base64Cipher());
    }

    public void put(String key, Object obj, Cipher cipher) {
        try {
            if (obj == null) {
                sp.edit().remove(key).commit();
            } else {
                byte[] bytes = ByteUtils.objectToByte(obj);
                if (cipher != null)
                    bytes = cipher.encrypt(bytes);
                put(key, HexUtils.encodeHexStr(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putString(String key, String value) {
        if (value == null) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public void putLong(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }



    public static void setPrefHighQuality(Context context, boolean isEnabled) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_HIGH_QUALITY, isEnabled);
        editor.apply();
    }

    public static boolean getPrefHighQuality(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREF_HIGH_QUALITY, false);
    }
}
