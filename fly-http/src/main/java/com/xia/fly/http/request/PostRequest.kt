package com.xia.fly.http.request

import com.xia.fly.http.body.UploadProgressRequestBody
import com.xia.fly.http.body.upload.ProgressResponseCallBack
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.UnsupportedEncodingException
import java.net.URLConnection
import java.net.URLEncoder

/**
 * @author xia
 * @date 2018/8/3.
 */
class PostRequest(url: String) : BaseBodyRequest<PostRequest>(url) {
    private var mUpdateFileCallback: ProgressResponseCallBack? = null//上传回调监听

    fun updateFileCallback(updateFileCallback: ProgressResponseCallBack?): PostRequest {
        mUpdateFileCallback = updateFileCallback
        return this
    }

    override fun generateRequest(): Observable<ResponseBody>? {
        if (mRequestBody != null) {
            return mRestService?.postBody(mUrl, mRequestBody!!, mHeaders)
        }
        if (mJson != null) {
            val body = RequestBody.create(mMediaType, mJson!!)
            return mRestService?.postJson(mUrl, body, mHeaders)
        }
        if (mObject != null) {
            return mRestService?.postBody(mUrl, mObject!!, mHeaders)
        }
        if (mBytes != null) {
            val body = RequestBody.create(mMediaType, mBytes!!)
            return mRestService?.postBody(mUrl, body, mHeaders)
        }
        if (mString != null) {
            val body = RequestBody.create(mMediaType, mString!!)
            return mRestService?.postBody(mUrl, body, mHeaders)
        }

        return if (mFileParams.isEmpty()) {
            mRestService?.post(mUrl, mParams, mHeaders)
        } else {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            val body = addParams(builder)
            mRestService?.uploadFiles(mUrl, body, mHeaders)
        }
    }

    private fun addParams(builder: MultipartBody.Builder): RequestBody {
        if (mParams.isNotEmpty()) {
            for ((key, value) in mParams) {
                builder.addFormDataPart(key, value)
            }
        }
        if (mFileParams.isNotEmpty()) {
            for ((key, file) in mFileParams) {
                val fileBody = RequestBody.create(MediaType.parse(guessMimeType(file.name)), file)
                val uploadProgressRequestBody = UploadProgressRequestBody(fileBody, mUpdateFileCallback)
                builder.addFormDataPart(key, file.name, uploadProgressRequestBody)
            }
        }
        return builder.build()
    }

    private fun guessMimeType(path: String): String {
        val fileNameMap = URLConnection.getFileNameMap()
        var contentTypeFor: String? = null
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return contentTypeFor ?: "application/octet-stream"
    }
}
