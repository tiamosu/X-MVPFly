package com.xia.baseproject.rxhttp.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "WeakerAccess"})
public final class FileUtils {

    private static final String SDCARD_DIR =
            Environment.getExternalStorageDirectory().getAbsolutePath();

    public static File createFile(String sdcardDirName, String fileName) {
        return new File(createDir(sdcardDirName), fileName);
    }

    public static File createDir(String sdcardDirName) {
        //拼接成SD卡中完整的dir
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }
}
