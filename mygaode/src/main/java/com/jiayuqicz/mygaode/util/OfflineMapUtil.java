package com.jiayuqicz.mygaode.util;

import android.os.Environment;

/**
 * Created by jiayuqicz on 2017/8/22 0022.
 */

public class OfflineMapUtil {

    /**
     * 获取map 缓存和读取目录
     */

    public static String getSdCacheDir() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            java.io.File fExternalStorageDirectory = Environment
                    .getExternalStorageDirectory();
            java.io.File autonaviDir = new java.io.File(
                    fExternalStorageDirectory, "amapsdk");
            if (!autonaviDir.exists()) {
                autonaviDir.mkdir();
            }
            java.io.File minimapDir = new java.io.File(autonaviDir,
                    "offlineMap");
            if (!minimapDir.exists()) {
                minimapDir.mkdir();
            }
            return minimapDir.toString() + "/";
        } else {
            return "";
        }
    }
}
