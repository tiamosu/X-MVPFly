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
            return BaseRequest.mRestService.deleteBody(mUrl, mRequestBody!!)
        }
        if (mJson != null) {
            val body = RequestBody.create(mMediaType, mJson!!)
            return BaseRequest.mRestService.deleteJson(mUrl, body)
        }
        if (mObject != null) {
            return BaseRequest.mRestService.deleteBody(mUrl, mObject!!)
        }
        if (mString != null) {
            val body = RequestBody.create(mMediaType, mString!!)
            return BaseRequest.mRestService.deleteBody(mUrl, body)
        }
        return BaseRequest.mRestService.delete(mUrl, mParams)
    }
}
