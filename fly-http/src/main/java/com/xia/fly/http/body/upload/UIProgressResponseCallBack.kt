package com.xia.fly.http.body.upload

import android.os.Handler
import android.os.Looper
import android.os.Message

import java.lang.ref.WeakReference

/**
 * @author xia
 * @date 2018/8/3.
 *
 *
 * 描述：可以直接更新UI的回调
 */
@Suppress("unused")
abstract class UIProgressResponseCallBack : ProgressResponseCallBack {

    //主线程Handler
    private val mHandler = UIHandler(Looper.getMainLooper(), apply {})

    //处理UI层的Handler子类
    private class UIHandler(looper: Looper, uiProgressResponseListener: UIProgressResponseCallBack) : Handler(looper) {
        //弱引用
        private val weakReference: WeakReference<UIProgressResponseCallBack> = WeakReference(uiProgressResponseListener)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                RESPONSE_UPDATE -> {
                    val uiProgressResponseListener = weakReference.get()
                    if (uiProgressResponseListener != null) {
                        //获得进度实体类
                        val progressModel = msg.obj as ProgressModel
                        //回调抽象方法
                        uiProgressResponseListener.onUIResponseProgress(progressModel.getCurrentBytes(),
                                progressModel.getContentLength(), progressModel.isDone())
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onResponseProgress(bytesWritten: Long, contentLength: Long, done: Boolean) {
        //通过Handler发送进度消息
        val message = Message.obtain()
        message.obj = ProgressModel(bytesWritten, contentLength, done)
        message.what = RESPONSE_UPDATE
        mHandler.sendMessage(message)
    }

    /**
     * UI层回调抽象方法
     *
     * @param bytesRead     当前读取响应体字节长度
     * @param contentLength 总字节长度
     * @param done          是否读取完成
     */
    abstract fun onUIResponseProgress(bytesRead: Long, contentLength: Long, done: Boolean)

    companion object {
        private const val RESPONSE_UPDATE = 0x02
    }
}
