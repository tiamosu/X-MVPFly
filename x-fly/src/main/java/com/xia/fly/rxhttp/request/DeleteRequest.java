package com.xia.fly.rxhttp.request;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/4.
 */
@SuppressWarnings("WeakerAccess")
public class DeleteRequest extends BaseBodyRequest {

    public DeleteRequest(@NonNull String url) {
        super(url);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (mRequestBody != null) {
            return mRestService.deleteBody(mUrl, mRequestBody);
        }
        if (mJson != null) {
            final RequestBody body = RequestBody.create(mMediaType, mJson);
            return mRestService.deleteJson(mUrl, body);
        }
        if (mObject != null) {
            return mRestService.deleteBody(mUrl, mObject);
        }
        if (mString != null) {
            final RequestBody body = RequestBody.create(mMediaType, mString);
            return mRestService.deleteBody(mUrl, body);
        }
        return mRestService.delete(mUrl, mParams);
    }
}
