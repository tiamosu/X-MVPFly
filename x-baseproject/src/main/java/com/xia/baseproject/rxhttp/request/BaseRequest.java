package com.xia.baseproject.rxhttp.request;

import android.support.annotation.NonNull;

import com.xia.baseproject.rxhttp.RestCreator;
import com.xia.baseproject.rxhttp.api.RestService;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public abstract class BaseRequest<R extends BaseRequest> {
    protected String mUrl;
    protected LinkedHashMap<String, String> mParams = new LinkedHashMap<>();
    protected LinkedHashMap<String, File> mFileParams = new LinkedHashMap<>();

    public BaseRequest(@NonNull String url) {
        mUrl = url;
    }

    public R addParam(String key, String value) {
        mParams.put(key, value);
        return (R) this;
    }

    public R params(@NonNull Map<String, String> params) {
        mParams.putAll(params);
        return (R) this;
    }

    public R addFileParam(String key, File file) {
        mFileParams.put(key, file);
        return (R) this;
    }

    public R fileParams(@NonNull Map<String, File> fileParams) {
        mFileParams.putAll(fileParams);
        return (R) this;
    }

    protected static final RestService mRestService = RestCreator.getRestService();

    protected abstract Observable<ResponseBody> generateRequest();

    public RequestCall build() {
        return new RequestCall(generateRequest());
    }
}
