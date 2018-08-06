package com.xia.baseproject.rxhttp.callback;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractStringCallback extends Callback<String> {

    public AbstractStringCallback(AppCompatActivity activity) {
        super(activity);
    }

    public AbstractStringCallback(Fragment fragment) {
        super(fragment);
    }

    @Override
    public String parseNetworkResponse(ResponseBody responseBody) throws Exception {
        return responseBody.string();
    }
}
