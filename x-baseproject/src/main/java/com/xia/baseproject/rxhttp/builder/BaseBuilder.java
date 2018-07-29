package com.xia.baseproject.rxhttp.builder;

import com.xia.baseproject.rxhttp.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public abstract class BaseBuilder<T extends BaseBuilder> {
    protected String mUrl;
    protected Object mTag;
    protected Map<String, String> mHeaders;
    protected Map<String, String> mParams;
    protected int mId;

    public T id(int id) {
        this.mId = id;
        return (T) this;
    }

    public T url(String url) {
        this.mUrl = url;
        return (T) this;
    }

    public T tag(Object tag) {
        this.mTag = tag;
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.mHeaders = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (mHeaders == null) {
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}
