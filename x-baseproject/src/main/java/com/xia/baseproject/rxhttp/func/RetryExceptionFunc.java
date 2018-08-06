package com.xia.baseproject.rxhttp.func;

import android.util.Log;

import com.xia.baseproject.rxhttp.exception.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * @author xia
 * @date 2018/8/6.
 * <p>
 * 描述：网络请求错误重试条件
 * </p>
 */
@SuppressWarnings("WeakerAccess")
public class RetryExceptionFunc implements Function<Observable<? extends Throwable>, Observable<?>> {

    //retry次数
    private int count = 3;
    //延迟
    private long delay = 500;
    //叠加延迟
    private long increaseDelay = 3000;

    public RetryExceptionFunc() {
    }

    public RetryExceptionFunc(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public RetryExceptionFunc(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) {
        return observable.zipWith(Observable.range(1, count + 1),
                (BiFunction<Throwable, Integer, Wrapper>) Wrapper::new)
                .flatMap((Function<Wrapper, ObservableSource<?>>) wrapper -> {
                    if (wrapper.index > 1) {
                        Log.i("Retry", "重试次数：" + (wrapper.index));
                    }
                    int errCode = 0;
                    if (wrapper.throwable instanceof ApiException) {
                        final ApiException throwable = (ApiException) wrapper.throwable;
                        errCode = throwable.getCode();
                    }
                    if ((wrapper.throwable instanceof ConnectException
                            || wrapper.throwable instanceof SocketTimeoutException
                            || errCode == ApiException.ERROR.NETWORD_ERROR
                            || errCode == ApiException.ERROR.TIMEOUT_ERROR
                            || wrapper.throwable instanceof SocketTimeoutException
                            || wrapper.throwable instanceof TimeoutException)
                            && wrapper.index < count + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                        return Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(wrapper.throwable);
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
