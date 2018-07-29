package com.xia.baseproject.rxhttp.request;

import com.xia.baseproject.rxhttp.RestCreator;
import com.xia.baseproject.rxhttp.api.RestService;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseRequest {

    protected static final RestService mRestService = RestCreator.getRestService();

    public RequestCall build() {
        return new RequestCall(this);
    }

    protected abstract RequestBody buildRequestBody();

    protected abstract Observable getObservable();

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}
