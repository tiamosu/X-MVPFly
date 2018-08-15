package com.xia.baseproject.rxhttp.callback;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.CloseUtils;
import com.xia.baseproject.rxhttp.utils.Platform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
public abstract class AbstractTextCallback extends Callback<String> {

    public AbstractTextCallback(@NonNull LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public String parseNetworkResponse(ResponseBody responseBody) throws Exception {
        final InputStream is = responseBody.byteStream();
        final Reader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        final StringBuilder builder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        final String result = builder.toString();
        Platform.post(mHttpTag, o -> onResponse(result));
        CloseUtils.closeIO(responseBody, is, reader, bufferedReader);
        return result;
    }
}
