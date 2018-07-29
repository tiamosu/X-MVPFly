package com.xia.baseproject.rxhttp;

import com.xia.baseproject.rxhttp.builder.GetBuilder;
import com.xia.baseproject.rxhttp.builder.PostBuilder;
import com.xia.baseproject.rxhttp.builder.PostFileBuilder;
import com.xia.baseproject.rxhttp.builder.PostStringBuilder;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public final class RestClient {

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static GetBuilder download() {
        final GetBuilder builder = new GetBuilder();
        builder.download();
        return builder;
    }

    public static PostBuilder post() {
        return new PostBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }
}
