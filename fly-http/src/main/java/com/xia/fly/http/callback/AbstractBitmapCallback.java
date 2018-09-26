package com.xia.fly.http.callback;

import android.arch.lifecycle.LifecycleOwner;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.CloseUtils;
import com.xia.fly.utils.Platform;

import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
public abstract class AbstractBitmapCallback extends Callback<Bitmap> {

    public AbstractBitmapCallback(@NonNull LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public void parseNetworkResponse(ResponseBody responseBody) {
        final InputStream is = responseBody.byteStream();
        final Bitmap bitmap = BitmapFactory.decodeStream(is);
        Platform.post(() -> onResponse(bitmap));
        CloseUtils.closeIO(responseBody, is);
    }
}
