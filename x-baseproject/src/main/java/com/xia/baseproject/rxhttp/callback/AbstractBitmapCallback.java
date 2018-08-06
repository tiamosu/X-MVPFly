package com.xia.baseproject.rxhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.CloseUtils;

import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
public abstract class AbstractBitmapCallback extends Callback<Bitmap> {

    public AbstractBitmapCallback(AppCompatActivity activity) {
        super(activity);
    }

    public AbstractBitmapCallback(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Bitmap parseNetworkResponse(ResponseBody responseBody) {
        final InputStream is = responseBody.byteStream();
        final Bitmap bitmap = BitmapFactory.decodeStream(is);
        CloseUtils.closeIO(is);
        return bitmap;
    }
}
