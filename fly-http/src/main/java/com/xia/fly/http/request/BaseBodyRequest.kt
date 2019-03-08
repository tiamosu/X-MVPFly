package com.xia.fly.http.request

import com.xia.fly.http.body.upload.ProgressResponseCallBack
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * @author xia
 * @date 2018/8/4.
 */
@Suppress("unused", "UNCHECKED_CAST")
abstract class BaseBodyRequest<R : BaseBodyRequest<R>>(url: String) : BaseRequest<R>(url) {
    @JvmField
    protected var mRequestBody: RequestBody? = null                  //自定义的请求体
    @JvmField
    protected var mJson: String? = null                              //上传的Json
    @JvmField
    protected var mObject: Any? = null                               //上传的对象
    @JvmField
    protected var mBytes: ByteArray? = null                          //上传的字节数据
    @JvmField
    protected var mString: String? = null                            //上传的文本内容
    @JvmField
    protected var mMediaType: MediaType? = null
    @JvmField
    protected var mUpdateFileCallback: ProgressResponseCallBack? = null//上传回调监听

    fun upRequestBody(requestBody: RequestBody): R {
        mRequestBody = requestBody
        return this as R
    }

    fun upJson(json: String): R {
        mJson = json
        mMediaType = MediaType.parse("application/json; charset=utf-8")
        return this as R
    }

    fun upObject(`object`: Any): R {
        mObject = `object`
        return this as R
    }

    fun upByte(bytes: ByteArray): R {
        mBytes = bytes
        mMediaType = MediaType.parse("application/octet-stream")
        return this as R
    }

    @JvmOverloads
    fun upString(string: String, mediaType: MediaType? = null): R {
        mString = string
        mMediaType = mediaType ?: MediaType.parse("text/plain; charset=utf-8")
        return this as R
    }

    fun updateFileCallback(updateFileCallback: ProgressResponseCallBack): R {
        mUpdateFileCallback = updateFileCallback
        return this as R
    }
}
