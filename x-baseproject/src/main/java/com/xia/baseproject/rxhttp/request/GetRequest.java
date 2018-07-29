package com.xia.baseproject.rxhttp.request;

import com.xia.baseproject.rxhttp.exception.Exceptions;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class GetRequest extends BaseRequest {
    private String mUrl;
    private boolean mIsDownload;

    public GetRequest(String url, boolean isDownload) {
        this.mUrl = url;
        this.mIsDownload = isDownload;

        if (this.mUrl == null) {
            Exceptions.illegalArgument("the mUrl can not be null !");
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Observable getObservable() {
        if (mIsDownload) {
            return mRestService.download(mUrl);
        }
        return mRestService.get(mUrl);
    }
}
