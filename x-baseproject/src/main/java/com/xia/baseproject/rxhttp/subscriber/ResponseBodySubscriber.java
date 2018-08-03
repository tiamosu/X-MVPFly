package com.xia.baseproject.rxhttp.subscriber;

import android.content.Context;

import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.utils.Platform;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public class ResponseBodySubscriber extends CallbackSubscriber<ResponseBody> {

    public ResponseBodySubscriber(Context context, Callback callback) {
        super(context, callback);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        super.onNext(responseBody);
        try {
            if (getCallback() != null) {
                final Object result = getCallback().parseNetworkResponse(responseBody);
                Platform.post(() -> getCallback().onResponse(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
    }
}
