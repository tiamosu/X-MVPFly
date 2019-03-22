package com.xia.fly.http.callback

import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.CloseUtils
import com.blankj.utilcode.util.ThreadUtils
import com.xia.fly.utils.FileUtils
import com.xia.fly.utils.Platform
import io.reactivex.functions.Action
import okhttp3.ResponseBody
import java.io.*

/**
 * @author xia
 * @date 2018/7/28.
 */
@Suppress("unused")
abstract class AbstractFileCallback(lifecycleOwner: LifecycleOwner,
                                    private val destFileDir: String,//目标文件存储的文件夹路径
                                    private val destFileName: String//目标文件存储的文件名
) : Callback<File>(lifecycleOwner) {

    private var mDownloadTask: DownloadTask? = null

    @Throws(Exception::class)
    override fun parseNetworkResponse(responseBody: ResponseBody) {
        if (mDownloadTask == null) {
            mDownloadTask = DownloadTask(responseBody)
            ThreadUtils.executeByIo(mDownloadTask)
        }
    }

    private inner class DownloadTask internal constructor
    (private val mResponseBody: ResponseBody) : ThreadUtils.SimpleTask<File>() {

        override fun doInBackground(): File? {
            return saveFile(mResponseBody)
        }

        override fun onSuccess(result: File?) {
            onResponse(result)
        }
    }

    private fun saveFile(responseBody: ResponseBody): File {
        val file = FileUtils.createFile(destFileDir, destFileName)
        val `is` = responseBody.byteStream()
        var bis: BufferedInputStream? = null
        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null

        try {
            bis = BufferedInputStream(`is`)
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)

            val fileSize = responseBody.contentLength()
            val data = ByteArray(1024 * 4)
            var read: Int
            var downloadSize: Long = 0
            var lastProgress = 0

            do {
                read = bis.read(data)
                if (read == -1) {
                    break
                }
                bos.write(data, 0, read)
                downloadSize += read.toLong()
                val progress = Math.round(downloadSize * 100f / fileSize)
                if (lastProgress != progress) {
                    Platform.post(Action { inProgress(progress.toFloat(), fileSize) })
                }
                lastProgress = progress
            } while (true)

            bos.flush()
            fos.flush()
        } catch (e: IOException) {
            onError(e)
        } finally {
            CloseUtils.closeIO(responseBody, bos, fos, bis, `is`)
        }
        return file
    }
}
