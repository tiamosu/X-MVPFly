package com.xia.baseproject.rxhttp.builder;

import android.net.Uri;

import com.xia.baseproject.rxhttp.request.GetRequest;
import com.xia.baseproject.rxhttp.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class GetBuilder extends BaseBuilder<GetBuilder> implements IParams {
    private boolean mIsDownload;

    @Override
    public GetBuilder params(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.mParams == null) {
            this.mParams = new LinkedHashMap<>();
        }
        this.mParams.put(key, val);
        return this;
    }

    public GetBuilder download() {
        this.mIsDownload = true;
        return this;
    }

    @Override
    public RequestCall build() {
        if (this.mParams != null && this.mParams.size() > 0) {
            this.mUrl = appendParams(mUrl, mParams);
        }
        return new GetRequest(mUrl, mIsDownload).build();
    }

    private String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        final Uri.Builder builder = Uri.parse(url).buildUpon();
        final Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            value = value == null ? "" : value;
            builder.appendQueryParameter(key, value);
        }
        return builder.build().toString();
    }
}
