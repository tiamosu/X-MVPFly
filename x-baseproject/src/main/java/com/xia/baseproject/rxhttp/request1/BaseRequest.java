package com.xia.baseproject.rxhttp.request1;

import com.xia.baseproject.rxhttp.RestCreator;
import com.xia.baseproject.rxhttp.api.RestService;
import com.xia.baseproject.rxhttp.exception.Exceptions;
import com.xia.baseproject.rxhttp.request.RequestCall;

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

    public BaseRequest(String url) {
        mUrl = url;
        if (mUrl == null) {
            Exceptions.illegalArgument("the mUrl can not be null !");
        }
    }

    public R addParams(String key, String value) {
        mParams.put(key, value);
        return (R) this;
    }

    public R params(Map<String, String> params) {
        mParams.putAll(params);
        return (R) this;
    }

    protected static final RestService mRestService = RestCreator.getRestService();

    protected abstract Observable<ResponseBody> generateRequest();

    public RequestCall build() {
        return new RequestCall();
    }
}
