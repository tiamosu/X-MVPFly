package com.xia.baseproject.rxhttp;

import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.subscriber.CustomObserver;

import java.io.File;
import java.util.Map;

/**
 * @author xia
 * @date 2018/7/29.
 */
public final class CustomClient {

    public static void get(String url, Callback callback) {
        get(url, null, true, callback);
    }

    public static void get(String url, Map<String, String> params, Callback callback) {
        get(url, params, true, callback);
    }

    public static void get(String url, Map<String, String> params,
                           boolean isShowDialog, Callback callback) {
        RestClient.get()
                .url(url)
                .params(params)
                .build()
                .request(new CustomObserver(callback) {
                    @Override
                    protected boolean isShowDialog() {
                        return isShowDialog;
                    }
                });
    }

    public static void post(String url, Callback callback) {
        post(url, null, true, callback);
    }

    public static void post(String url, Map<String, String> params, Callback callback) {
        post(url, params, true, callback);
    }

    public static void post(String url, Map<String, String> params,
                            boolean isShowDialog, Callback callback) {
        RestClient.post()
                .url(url)
                .params(params)
                .build()
                .request(new CustomObserver(callback) {
                    @Override
                    protected boolean isShowDialog() {
                        return isShowDialog;
                    }
                });
    }

    public static void download(String url, Map<String, String> params,
                                boolean isShowDialog, Callback callback) {
        RestClient
                .download()
                .url(url)
                .params(params)
                .build()
                .request(new CustomObserver(callback) {
                    @Override
                    protected boolean isShowDialog() {
                        return isShowDialog;
                    }
                });
    }

    public static void upload(String url, Map<String, File> fileParams, Callback callback) {
        RestClient
                .post()
                .url(url)
                .fileParams(fileParams)
                .build()
                .request(new CustomObserver(callback));
    }
}
