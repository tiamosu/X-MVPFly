package com.xia.baseproject.rxhttp.request;

import android.text.TextUtils;

import com.xia.baseproject.rxhttp.exception.Exceptions;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class OtherRequest extends BaseRequest {
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private String mUrl;
    private RequestBody mRequestBody;
    private String mMethod;
    private String mContent;

    public OtherRequest(String url, RequestBody requestBody, String method, String content) {
        this.mUrl = url;
        this.mRequestBody = requestBody;
        this.mMethod = method;
        this.mContent = content;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (this.mUrl == null) {
            Exceptions.illegalArgument("the mUrl can not be null !");
        }
        if (mRequestBody == null && TextUtils.isEmpty(mContent) && HttpMethod.requiresRequestBody(mMethod)) {
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + mMethod);
        }
        if (mRequestBody == null && !TextUtils.isEmpty(mContent)) {
            mRequestBody = RequestBody.create(MEDIA_TYPE_PLAIN, mContent);
        }
        return mRequestBody;
    }

    @Override
    protected Observable getObservable() {
        switch (mMethod) {
            case METHOD.PUT:
                return mRestService.put(mUrl, mRequestBody);
            case METHOD.DELETE:
                if (mRequestBody == null) {
                    return mRestService.delete(mUrl);
                } else {
                    return mRestService.delete(mUrl, mRequestBody);
                }
            case METHOD.HEAD:
                return mRestService.head(mUrl);
            case METHOD.PATCH:
                return mRestService.patch(mUrl, mRequestBody);
            default:
                return null;
        }
    }
}
