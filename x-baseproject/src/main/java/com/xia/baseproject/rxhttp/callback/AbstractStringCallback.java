package com.xia.baseproject.rxhttp.callback;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractStringCallback extends Callback<String> {

    public AbstractStringCallback(@NonNull LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public String parseNetworkResponse(ResponseBody responseBody) throws Exception {
        return responseBody.string();
    }
}
