package com.xia.baseproject.rxhttp.request;

import com.xia.baseproject.rxhttp.exception.Exceptions;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class PostStringRequest extends BaseRequest {
    private static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private String mUrl;
    private String mContent;
    private MediaType mMediaType;

    public PostStringRequest(String url, String content, MediaType mediaType) {
        this.mUrl = url;
        this.mContent = content;
        this.mMediaType = mediaType;

        if (this.mUrl == null) {
            Exceptions.illegalArgument("the mUrl can not be null !");
        }
        if (this.mContent == null) {
            Exceptions.illegalArgument("the mContent can not be null !");
        }
        if (this.mMediaType == null) {
            this.mMediaType = MEDIA_TYPE_PLAIN;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType, mContent);
    }

    @Override
    protected Observable getObservable() {
        return mRestService.post(mUrl, buildRequestBody());
    }
}

