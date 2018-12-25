package com.xia.fly.http.request;

import com.xia.fly.http.body.upload.ProgressResponseCallBack;

import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author xia
 * @date 2018/8/4.
 */
@SuppressWarnings({"WeakerAccess", "unchecked"})
public abstract class BaseBodyRequest<R extends BaseBodyRequest> extends BaseRequest<R> {
    protected RequestBody mRequestBody;                   //自定义的请求体
    protected String mJson;                               //上传的Json
    protected Object mObject;                             //上传的对象
    protected byte[] mBytes;                              //上传的字节数据
    protected String mString;                             //上传的文本内容
    protected MediaType mMediaType;
    protected ProgressResponseCallBack mUpdateFileCallback;//上传回调监听

    public BaseBodyRequest(@NonNull String url) {
        super(url);
    }

    public R upRequestBody(@NonNull RequestBody requestBody) {
        mRequestBody = requestBody;
        return (R) this;
    }

    public R upJson(@NonNull String json) {
        mJson = json;
        mMediaType = MediaType.parse("application/json; charset=utf-8");
        return (R) this;
    }

    public R upObject(@NonNull Object object) {
        mObject = object;
        return (R) this;
    }

    public R upByte(@NonNull byte[] bytes) {
        mBytes = bytes;
        mMediaType = MediaType.parse("application/octet-stream");
        return (R) this;
    }

    public R upString(@NonNull String string) {
        return upString(string, null);
    }

    public R upString(@NonNull String string, MediaType mediaType) {
        mString = string;
        mMediaType = mediaType != null ? mediaType : MediaType.parse("text/plain; charset=utf-8");
        return (R) this;
    }

    public R updateFileCallback(ProgressResponseCallBack updateFileCallback) {
        mUpdateFileCallback = updateFileCallback;
        return (R) this;
    }
}
