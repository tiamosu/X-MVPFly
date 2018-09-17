package com.xia.fly.rxhttp;

import android.support.annotation.NonNull;

import com.xia.fly.rxhttp.request.DeleteRequest;
import com.xia.fly.rxhttp.request.GetRequest;
import com.xia.fly.rxhttp.request.PostRequest;
import com.xia.fly.rxhttp.request.PutRequest;

/**
 * @author xia
 * @date 2018/7/29.
 */
public final class RxHttp {

    public static GetRequest get(@NonNull String url) {
        return new GetRequest(url);
    }

    public static PostRequest post(@NonNull String url) {
        return new PostRequest(url);
    }

    public static DeleteRequest delete(@NonNull String url) {
        return new DeleteRequest(url);
    }

    public static PutRequest put(@NonNull String url) {
        return new PutRequest(url);
    }
}
