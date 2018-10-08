package com.xia.fly.http.request;

import android.support.annotation.NonNull;

import com.xia.fly.http.api.RestService;
import com.xia.fly.http.utils.ParamsUtils;
import com.xia.fly.utils.FlyUtils;

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
    protected final String mUrl;
    protected final LinkedHashMap<String, String> mParams = new LinkedHashMap<>();
    protected final LinkedHashMap<String, File> mFileParams = new LinkedHashMap<>();

    public BaseRequest(@NonNull String url) {
        mUrl = url;
    }

    public R addParam(String key, String value) {
        value = value == null ? "" : value;
        mParams.put(key, value);
        return (R) this;
    }

    public R params(Map<String, String> params) {
        if (params != null) {
            mParams.putAll(params);
            ParamsUtils.escapeParams(mParams);
        }
        return (R) this;
    }

    public R addFileParam(String key, File file) {
        mFileParams.put(key, file);
        return (R) this;
    }

    public R fileParams(Map<String, File> fileParams) {
        if (fileParams != null) {
            mFileParams.putAll(fileParams);
        }
        return (R) this;
    }

    protected static final RestService mRestService =
            FlyUtils.getAppComponent().repositoryManager()
                    .obtainRetrofitService(RestService.class);

    protected abstract Observable<ResponseBody> generateRequest();

    public RequestCall build() {
        return new RequestCall(generateRequest());
    }
}
