package com.xia.baseproject.rxhttp.utils;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

/**
 * @author xia
 * @date 2018/4/24.
 */
public class Platform {

    @SuppressWarnings("unchecked")
    public static void post(final Action action) {
        Completable.fromAction(action)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
