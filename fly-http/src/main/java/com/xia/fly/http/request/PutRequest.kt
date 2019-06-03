package com.xia.fly.http.request

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 * @author xia
 * @date 2018/8/4.
 */
class PutRequest(url: String) : BaseBodyRequest<PutRequest>(url) {

    override fun generateRequest(): Observable<ResponseBody> {
        if (mRequestBody != null) {
            return mRestService.putBody(mUrl, mRequestBody!!, mHeaders)
        }
        if (mJson != null) {
            val body = RequestBody.create(mMediaType, mJson!!)
            return mRestService.putJson(mUrl, body, mHeaders)
        }
        if (mObject != null) {
            return mRestService.putBody(mUrl, mObject!!, mHeaders)
        }
        if (mString != null) {
            val body = RequestBody.create(mMediaType, mString!!)
            return mRestService.putBody(mUrl, body, mHeaders)
        }
        return mRestService.put(mUrl, mParams, mHeaders)
    }
}
