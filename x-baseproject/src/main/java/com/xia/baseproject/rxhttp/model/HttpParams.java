package com.xia.baseproject.rxhttp.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xia
 * @date 2018/8/3.
 */
public class HttpParams implements Serializable {

    public LinkedHashMap<String, String> mUrlParams;

    public HttpParams() {
        mUrlParams = new LinkedHashMap<>();
    }

    public void put(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            mUrlParams.putAll(params);
        }
    }

    public void put(String key, String value) {
        mUrlParams.put(key, value);
    }
}
