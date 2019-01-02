package com.xia.fly.http.callback;

import com.blankj.utilcode.util.CloseUtils;
import com.xia.fly.utils.Platform;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.functions.Action;
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
    public void parseNetworkResponse(ResponseBody responseBody) throws Exception {
        final String result = responseBody.string();
        Platform.post(new Action() {
            @Override
            public void run() {
                onResponse(result);
            }
        });
        CloseUtils.closeIO(responseBody);
    }
}