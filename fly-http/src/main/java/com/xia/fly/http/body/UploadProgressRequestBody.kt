package com.xia.fly.http.body

import com.xia.fly.http.body.upload.ProgressResponseCallBack
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * @author xia
 * @date 2018/8/3.
 *
 * 描述：上传请求体
 * 1.具有上传进度回调通知功能<br></br>
 * 2.防止频繁回调，上层无用的刷新<br></br>
 */
@Suppress("unused")
class UploadProgressRequestBody : RequestBody {
    private var delegate: RequestBody? = null
    private val progressCallBack: ProgressResponseCallBack?

    constructor(listener: ProgressResponseCallBack) {
        this.progressCallBack = listener
    }

    constructor(delegate: RequestBody, progressCallBack: ProgressResponseCallBack) {
        this.delegate = delegate
        this.progressCallBack = progressCallBack
    }

    fun setRequestBody(delegate: RequestBody) {
        this.delegate = delegate
    }

    override fun contentType(): MediaType? {
        return delegate?.contentType()
    }

    /**
     * 重写调用实际的响应体的contentLength
     */
    override fun contentLength(): Long {
        return try {
            delegate?.contentLength() ?: 0
        } catch (e: IOException) {
            -1
        }
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = Okio.buffer(countingSink)
        delegate?.writeTo(bufferedSink)
        bufferedSink.flush()
    }

    private inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {
        private var bytesWritten: Long = 0
        private var contentLength: Long = 0  //总字节长度，避免多次调用contentLength()方法
        private var lastRefreshUiTime: Long = 0  //最后一次刷新的时间

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            if (contentLength <= 0) {
                contentLength = contentLength() //获得contentLength的值，后续不再调用
            }
            //增加当前写入的字节数
            bytesWritten += byteCount

            val curTime = System.currentTimeMillis()
            //每100毫秒刷新一次数据,防止频繁无用的刷新
            if (curTime - lastRefreshUiTime >= 100 || bytesWritten == contentLength) {
                progressCallBack?.onResponseProgress(bytesWritten, contentLength, bytesWritten == contentLength)
                lastRefreshUiTime = System.currentTimeMillis()
            }
        }
    }
}

