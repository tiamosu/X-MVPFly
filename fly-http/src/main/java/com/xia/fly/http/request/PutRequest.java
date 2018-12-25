package com.xia.fly.http.request;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/4.
 */
public class PutRequest extends BaseBodyRequest<PutRequest> {

    public PutRequest(@NonNull String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (mRequestBody != null) {
            return mRestService.putBody(mUrl, mRequestBody);
        }
        if (mJson != null) {
            final RequestBody body = RequestBody.create(mMediaType, mJson);
            return mRestService.putJson(mUrl, body);
        }
        if (mObject != null) {
            return mRestService.putBody(mUrl, mObject);
        }
        if (mString != null) {
            RequestBody body = RequestBody.create(mMediaType, mString);
            return mRestService.putBody(mUrl, body);
        }
        return mRestService.put(mUrl, mParams);
    }
}
