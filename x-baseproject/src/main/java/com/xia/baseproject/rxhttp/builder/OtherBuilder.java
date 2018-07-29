package com.xia.baseproject.rxhttp.builder;

import com.xia.baseproject.rxhttp.request.OtherRequest;
import com.xia.baseproject.rxhttp.request.RequestCall;

import okhttp3.RequestBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class OtherBuilder extends BaseBuilder<OtherBuilder> {
    private RequestBody mRequestBody;
    private String mMethod;
    private String mContent;

    public OtherBuilder(String method) {
        this.mMethod = method;
    }

    @Override
    public RequestCall build() {
        return new OtherRequest(mUrl, mRequestBody, mMethod, mContent).build();
    }

    public OtherBuilder requestBody(RequestBody requestBody) {
        this.mRequestBody = requestBody;
        return this;
    }

    public OtherBuilder requestBody(String content) {
        this.mContent = content;
        return this;
    }
}
