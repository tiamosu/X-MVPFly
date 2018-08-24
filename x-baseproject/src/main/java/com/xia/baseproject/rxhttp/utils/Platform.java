package com.xia.baseproject.rxhttp.utils;

import android.os.Handler;
import android.os.Looper;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

/**
 * @author xia
 * @date 2018/4/24.
 */
public class Platform {

    private static final Handler MAIN_HANDLER;

    static {
        Looper looper;
        try {
            looper = Looper.getMainLooper();
        } catch (Exception e) {
            looper = null;
        }
        if (looper != null) {
            MAIN_HANDLER = new Handler(looper);
        } else {
            MAIN_HANDLER = null;
        }
    }

    public static void post(final Runnable runnable) {
        if (MAIN_HANDLER != null) {
            MAIN_HANDLER.post(runnable);
        } else {
            runnable.run();
        }
    }

    @SuppressWarnings("unchecked")
    public static void post(final Action action) {
        Completable.fromAction(action)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
