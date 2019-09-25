package com.xia.fly.http.request

import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * @author xia
 * @date 2018/8/3.
 */
@Suppress("unused")
class GetRequest(url: String) : BaseRequest<GetRequest>(url) {
    private var mIsDownload: Boolean = false

    fun upDownload(): GetRequest {
        mIsDownload = true
        return this
    }

    override fun generateRequest(): Observable<ResponseBody>? {
        return if (mIsDownload) {
            mRestService?.downloadFile(mUrl, mHeaders)
        } else mRestService?.get(mUrl, mParams, mHeaders)
    }
}
