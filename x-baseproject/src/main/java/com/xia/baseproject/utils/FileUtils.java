package com.xia.baseproject.utils;

import android.os.Environment;

import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public final class FileUtils {

    private static final String SDCARD_DIR =
            Environment.getExternalStorageDirectory().getAbsolutePath();

    public static File createFile(String sdcardDirName, String fileName) {
        return new File(createDir(sdcardDirName), fileName);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createDir(String sdcardDirName) {
        //拼接成SD卡中完整的dir
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        return createOrExistsDir(fileDir);
    }

    /**
     * @return 创建未存在的文件夹
     */
    public static File createOrExistsDir(final File file) {
        final boolean isExist = file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
        return isExist ? file : null;
    }

    /**
     * 返回缓存文件夹
     */
    public static File getCacheFile() {
        if (SDCardUtils.isSDCardEnableByEnvironment()) {
            File file = Utils.getApp().getExternalCacheDir();//获取系统管理的sd卡缓存文件
            if (file == null) {//如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
                file = createDir(Utils.getApp().getPackageName());
            }
            return file;
        } else {
            return Utils.getApp().getCacheDir();
        }
    }
}
