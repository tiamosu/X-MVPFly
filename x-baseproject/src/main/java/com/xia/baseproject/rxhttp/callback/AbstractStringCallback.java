package com.xia.baseproject.rxhttp.callback;

import android.content.Context;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractStringCallback extends Callback<String> {

    public AbstractStringCallback(Context context) {
        super(context);
    }

    @Override
    public String parseNetworkResponse(ResponseBody responseBody) throws Exception {
        return responseBody.string();
    }
}
