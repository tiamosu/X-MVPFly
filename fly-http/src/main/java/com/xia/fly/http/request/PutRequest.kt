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
            return BaseRequest.mRestService.putBody(mUrl, mRequestBody!!)
        }
        if (mJson != null) {
            val body = RequestBody.create(mMediaType, mJson!!)
            return BaseRequest.mRestService.putJson(mUrl, body)
        }
        if (mObject != null) {
            return BaseRequest.mRestService.putBody(mUrl, mObject!!)
        }
        if (mString != null) {
            val body = RequestBody.create(mMediaType, mString!!)
            return BaseRequest.mRestService.putBody(mUrl, body)
        }
        return BaseRequest.mRestService.put(mUrl, mParams)
    }
}
