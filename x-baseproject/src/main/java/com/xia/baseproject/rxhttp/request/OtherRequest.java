package com.xia.baseproject.rxhttp.request;

import android.support.annotation.NonNull;

import com.xia.baseproject.rxhttp.method.HttpMethod;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/4.
 */
public class OtherRequest extends BaseRequest<OtherRequest> {
    private String mMethod;
    private RequestBody mRequestBody;

    public OtherRequest(@NonNull String url) {
        super(url);
    }

    public OtherRequest upMethod(@HttpMethod.method String method) {
        mMethod = method;
        return this;
    }

    public OtherRequest upPatch(@NonNull RequestBody requestBody) {
        mRequestBody = requestBody;
        return this;
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        switch (mMethod) {
            case HttpMethod.HEAD:
                return mRestService.head(mUrl);
            case HttpMethod.PATCH:
                if (mRequestBody != null) {
                    return mRestService.patch(mUrl, mRequestBody);
                }
            default:
                return null;
        }
    }
}
