package com.xia.baseproject.rxhttp.builder;

import com.xia.baseproject.rxhttp.request.PostFileRequest;
import com.xia.baseproject.rxhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class PostFileBuilder extends BaseBuilder<PostFileBuilder> {
    private File mFile;
    private MediaType mMediaType;

    public PostFileBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public PostFileBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(mUrl, mFile, mMediaType).build();
    }
}

