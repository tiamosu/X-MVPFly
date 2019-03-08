package com.xia.fly.http.request

import com.xia.fly.http.body.UploadProgressRequestBody
import io.reactivex.Observable
import okhttp3.*
import java.io.UnsupportedEncodingException
import java.net.URLConnection
import java.net.URLEncoder

/**
 * @author xia
 * @date 2018/8/3.
 */
class PostRequest(url: String) : BaseBodyRequest<PostRequest>(url) {

    override fun generateRequest(): Observable<ResponseBody> {
        if (mRequestBody != null) {
            return BaseRequest.mRestService.postBody(mUrl, mRequestBody!!)
        }
        if (mJson != null) {
            val body = RequestBody.create(mMediaType, mJson!!)
            return BaseRequest.mRestService.postJson(mUrl, body)
        }
        if (mObject != null) {
            return BaseRequest.mRestService.postBody(mUrl, mObject!!)
        }
        if (mBytes != null) {
            val body = RequestBody.create(mMediaType, mBytes!!)
            return BaseRequest.mRestService.postBody(mUrl, body)
        }
        if (mString != null) {
            val body = RequestBody.create(mMediaType, mString!!)
            return BaseRequest.mRestService.postBody(mUrl, body)
        }

        return if (mFileParams.isEmpty()) {
            BaseRequest.mRestService.post(mUrl, mParams)
        } else {
            val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
            val body = addParams(builder)
            BaseRequest.mRestService.uploadFiles(mUrl, body)
        }
    }

    private fun addParams(builder: MultipartBody.Builder): RequestBody {
        if (!mParams.isEmpty()) {
            for ((key, value) in mParams) {
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"$key\""),
                        RequestBody.create(null, value))
            }
        }
        if (!mFileParams.isEmpty()) {
            for ((key, file) in mFileParams) {
                val fileBody = RequestBody.create(
                        MediaType.parse(guessMimeType(file.name)), file)
                val uploadProgressRequestBody = UploadProgressRequestBody(fileBody, mUpdateFileCallback!!)
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
        return contentTypeFor?:"application/octet-stream"
    }
}
