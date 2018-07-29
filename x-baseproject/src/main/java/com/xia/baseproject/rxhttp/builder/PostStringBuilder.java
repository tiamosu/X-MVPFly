package com.xia.baseproject.rxhttp.builder;

import com.xia.baseproject.rxhttp.request.PostStringRequest;
import com.xia.baseproject.rxhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class PostStringBuilder extends BaseBuilder<PostStringBuilder> {
    private String mContent;
    private MediaType mMediaType;

    public PostStringBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(mUrl, mContent, mMediaType).build();
    }
}

