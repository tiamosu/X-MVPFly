package com.xia.baseproject.rxhttp.callback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blankj.utilcode.util.CloseUtils;

import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
public abstract class AbstractBitmapCallback extends Callback<Bitmap> {

    public AbstractBitmapCallback(Context context) {
        super(context);
    }

    @Override
    public Bitmap parseNetworkResponse(ResponseBody responseBody) {
        final InputStream is = responseBody.byteStream();
        final Bitmap bitmap = BitmapFactory.decodeStream(is);
        CloseUtils.closeIO(is);
        return bitmap;
    }
}
