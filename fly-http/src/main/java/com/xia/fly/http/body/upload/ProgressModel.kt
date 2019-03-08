package com.xia.fly.http.body.upload

import java.io.Serializable

/**
 * @author xia
 * @date 2018/9/5.
 */
@Suppress("unused")
class ProgressModel internal constructor(//当前读取字节长度
        private var currentBytes: Long, //总字节长度
        private var contentLength: Long, //是否读取完成
        private var done: Boolean) : Serializable {

    fun getCurrentBytes(): Long {
        return currentBytes
    }

    fun setCurrentBytes(currentBytes: Long): ProgressModel {
        this.currentBytes = currentBytes
        return this
    }

    fun getContentLength(): Long {
        return contentLength
    }

    fun setContentLength(contentLength: Long): ProgressModel {
        this.contentLength = contentLength
        return this
    }

    fun isDone(): Boolean {
        return done
    }

    fun setDone(done: Boolean): ProgressModel {
        this.done = done
        return this
    }

    override fun toString(): String {
        return "ProgressModel{" +
                "currentBytes=" + currentBytes +
                ", contentLength=" + contentLength +
                ", done=" + done +
                '}'.toString()
    }
}
