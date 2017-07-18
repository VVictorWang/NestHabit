package com.victor.nesthabit.utils;

import android.content.Context;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by victor on 7/18/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MusicManger {
    public static final String TAG = "@victor MusicManger";
    public static String[] getMusic(Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media
                        .INTERNAL_CONTENT_URI, null, null,
                null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media
                    .DISPLAY_NAME));
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

        }
        String[] names = cursor.getColumnNames();
        return names;

    }

    public static Uri getMusicUri(Context context) {
        return RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE);
    }
}
