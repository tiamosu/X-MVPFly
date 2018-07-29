package com.xia.baseproject.rxhttp.request;

import com.xia.baseproject.rxhttp.exception.Exceptions;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class PostFileRequest extends BaseRequest {
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private String mUrl;
    private File mFile;
    private MediaType mMediaType;

    public PostFileRequest(String url, File file, MediaType mediaType) {
        this.mUrl = url;
        this.mFile = file;
        this.mMediaType = mediaType;

        if (this.mUrl == null) {
            Exceptions.illegalArgument("the mUrl can not be null !");
        }
        if (this.mFile == null) {
            Exceptions.illegalArgument("the mFile can not be null !");
        }
        if (this.mMediaType == null) {
            this.mMediaType = MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType, mFile);
    }

    @Override
    protected Observable getObservable() {
        return mRestService.post(mUrl, buildRequestBody());
    }
}
