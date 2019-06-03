package com.xia.fly.http.request

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 * @author xia
 * @date 2018/8/4.
 */
class DeleteRequest(url: String) : BaseBodyRequest<DeleteRequest>(url) {

    override fun generateRequest(): Observable<ResponseBody> {
        if (mRequestBody != null) {
            return mRestService.deleteBody(mUrl, mRequestBody!!, mHeaders)
        }
        if (mJson != null) {
            val body = RequestBody.create(mMediaType, mJson!!)
            return mRestService.deleteJson(mUrl, body, mHeaders)
        }
        if (mObject != null) {
            return mRestService.deleteBody(mUrl, mObject!!, mHeaders)
        }
        if (mString != null) {
            val body = RequestBody.create(mMediaType, mString!!)
            return mRestService.deleteBody(mUrl, body, mHeaders)
        }
        return mRestService.delete(mUrl, mParams, mHeaders)
    }
}
