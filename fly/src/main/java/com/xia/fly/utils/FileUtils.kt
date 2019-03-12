package com.xia.fly.utils

import android.os.Environment

import com.blankj.utilcode.util.SDCardUtils
import com.blankj.utilcode.util.Utils

import java.io.File

/**
 * @author xia
 * @date 2018/7/28.
 */
class FileUtils private constructor() {

    init {
        throw IllegalStateException("u can't instantiate me!")
    }

    companion object {

        private val SDCARD_DIR = Environment.getExternalStorageDirectory().absolutePath

        @JvmStatic
        fun createFile(sdcardDirName: String, fileName: String): File {
            return File(createDir(sdcardDirName), fileName)
        }

        @JvmStatic
        fun createDir(sdcardDirName: String): File? {
            //拼接成SD卡中完整的dir
            val dir = "$SDCARD_DIR/$sdcardDirName/"
            val fileDir = File(dir)
            return createOrExistsDir(fileDir)
        }

        /**
         * @return 创建未存在的文件夹
         */
        @JvmStatic
        fun createOrExistsDir(file: File?): File? {
            val isExist = file != null && if (file.exists()) file.isDirectory else file.mkdirs()
            return if (isExist) file else null
        }

        /**
         * 返回缓存文件夹
         */
        @JvmStatic
        fun getCacheFile(): File {
            return if (SDCardUtils.isSDCardEnableByEnvironment()) {
                //获取系统管理的sd卡缓存文件
                var file = Utils.getApp().externalCacheDir
                //如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
                file = file ?: createDir(Utils.getApp().packageName)
                file!!
            } else {
                Utils.getApp().cacheDir
            }
        }
    }
}
