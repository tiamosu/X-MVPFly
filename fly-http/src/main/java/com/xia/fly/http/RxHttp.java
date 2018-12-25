package com.xia.fly.http;

import com.xia.fly.http.request.DeleteRequest;
import com.xia.fly.http.request.GetRequest;
import com.xia.fly.http.request.PostRequest;
import com.xia.fly.http.request.PutRequest;

import androidx.annotation.NonNull;

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
