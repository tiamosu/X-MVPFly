package com.xia.fly.http.utils;

import android.util.Log;

import java.io.IOException;
import java.net.SocketException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author weixia
 * @date 2018/12/21.
 */
public final class RxJavaUtils {
    private static final String TAG = RxJavaUtils.class.getSimpleName();

    public static void setRxJavaErrorHandler() {
        if (RxJavaPlugins.getErrorHandler() != null || RxJavaPlugins.isLockdown()) {
            return;
        }
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if ((e instanceof SocketException) || (e instanceof IOException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            Log.e(TAG, "Undeliverable exception received, not sure what to do", e);
        });
    }
}
