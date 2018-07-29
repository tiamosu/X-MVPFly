package com.xia.baseproject.rxhttp.builder;

import com.xia.baseproject.rxhttp.request.PostRequest;
import com.xia.baseproject.rxhttp.request.RequestCall;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class PostBuilder extends BaseBuilder<PostBuilder> implements IParams {
    private Map<String, File> mFileParams;

    @Override
    public PostBuilder params(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    @Override
    public PostBuilder addParams(String key, String val) {
        if (this.mParams == null) {
            this.mParams = new LinkedHashMap<>();
        }
        this.mParams.put(key, val);
        return this;
    }

    public PostBuilder addFile(String key, File file) {
        if (this.mFileParams == null) {
            this.mFileParams = new LinkedHashMap<>();
        }
        this.mFileParams.put(key, file);
        return this;
    }

    public PostBuilder fileParams(Map<String, File> fileParams) {
        this.mFileParams = fileParams;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostRequest(mUrl, mParams, mFileParams).build();
    }
}

