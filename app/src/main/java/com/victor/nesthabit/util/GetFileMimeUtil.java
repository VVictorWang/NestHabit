package com.victor.nesthabit.util;

import java.io.File;

/**
 * @author victor
 * @date 11/20/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class GetFileMimeUtil {
    private static final String[][] MIME_MapTable = {
            {".jpg", "image/jpeg"},
            {".jpeg", "image/jpeg"},
            {".png", "image/png"},
            {".bmp", "image/bmp"},
            {".net", "image/pnetvue"},
            {".rp", "image/vnd.rn-realpix"},
            {".fax", "image/fax"},
            {".ico", "image/x-icon"},
            {".gif", "image/gif"},
            {".ogg", "audio/ogg"},
            {".lar", "audio/x-laplayer-reg"},
            {".lavs", "audio/x-liquid-secure"},
            {".lmsff", "audio/x-la-lms"},
            {".mid", "audio/mid"},
            {".mnd", "audio/x-musicnet-download"},
            {".mp1", "audio/mp1"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {"", "*/*"}
    };

    public static String getFileMime(File file) {
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (suffix.equals(MIME_MapTable[i][0])) {
                return MIME_MapTable[i][1];
            }
        }
        return "*/*";
    }
}
