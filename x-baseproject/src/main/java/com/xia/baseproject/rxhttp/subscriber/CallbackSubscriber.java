package com.xia.baseproject.rxhttp.subscriber;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.CloseUtils;
import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.utils.Platform;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class CallbackSubscriber extends ProgressSubscriber<ResponseBody> {
    public Callback mCallback;

    public CallbackSubscriber(@NonNull Callback callback) {
        super(callback.mContext);
        mCallback = callback;
    }

    @Override
    public void doOnSubscribe(Disposable disposable) {
        super.doOnSubscribe(disposable);
        mCallback.onSubscribe(disposable);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        super.onNext(responseBody);
        try {
            final Object result = mCallback.parseNetworkResponse(responseBody);
            if (result != null) {
                Platform.post(() -> mCallback.onResponse(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(responseBody);
        }
    }

    @Override
    public void doOnError(String error) {
        super.doOnError(error);
        mCallback.onError(error);
    }

    @Override
    public void doonComplete() {
        super.doonComplete();
        mCallback.onComplete();
    }
}
