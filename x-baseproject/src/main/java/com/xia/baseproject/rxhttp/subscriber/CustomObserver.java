package com.xia.baseproject.rxhttp.subscriber;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ThreadUtils;
import com.xia.baseproject.rxhttp.callback.Callback;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public class CustomObserver extends BaseObserver<ResponseBody> {
    private WeakReference<Callback> mCallback;

    public CustomObserver(Callback callback) {
        super(callback == null ? null : callback.getContext());
        this.mCallback = new WeakReference<>(callback);
    }

    private Callback getCallback() {
        final boolean isNull = mCallback == null || mCallback.get() == null;
        return isNull ? null : mCallback.get();
    }

    @Override
    public void doOnSubscribe(Disposable d) {
        if (getCallback() != null) {
            getCallback().onSubscribe(d);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doOnNext(ResponseBody responseBody) {
        if (getCallback() != null) {
            getCallback().onNext(responseBody);
            ThreadUtils.executeByIo(new parseTask(responseBody));
        }
    }

    @Override
    public void doOnError(String message) {
        if (getCallback() != null) {
            getCallback().onError(message);
        }
    }

    @Override
    public void doOnCompleted() {
        if (getCallback() != null) {
            getCallback().onComplete();
        }
    }

    private class parseTask extends ThreadUtils.SimpleTask {
        private ResponseBody mResponseBody;

        public parseTask(ResponseBody responseBody) {
            mResponseBody = responseBody;
        }

        @Nullable
        @Override
        public Object doInBackground() throws Throwable {
            Object result = null;
            if (getCallback() != null) {
                result = getCallback().parseNetworkResponse(mResponseBody);
            }
            return result;
        }

        @SuppressWarnings({"ConstantConditions", "unchecked"})
        @Override
        public void onSuccess(@Nullable Object result) {
            if (getCallback() != null) {
                getCallback().onResponse(result);
            }
            mResponseBody.close();
        }
    }
}
