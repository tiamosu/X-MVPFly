package com.xia.fly.http.request;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
public class GetRequest extends BaseRequest<GetRequest> {
    private boolean mIsDownload;

    public GetRequest(@NonNull String url) {
        super(url);
    }

    public GetRequest upDownload() {
        mIsDownload = true;
        return this;
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (mIsDownload) {
            return mRestService.downloadFile(mUrl);
        }
        return mRestService.get(mUrl, mParams);
    }
}
