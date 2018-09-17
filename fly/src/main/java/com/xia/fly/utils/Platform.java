package com.xia.fly.utils;

import android.os.Handler;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

/**
 * @author xia
 * @date 2018/4/24.
 */
public final class Platform {
    private static final Handler HANDLER = new Handler();

    public static Handler getHandler() {
        return HANDLER;
    }

    public static final boolean DEPENDENCY_GLIDE;

    static {
        DEPENDENCY_GLIDE = findClassByClassName("com.bumptech.glide.Glide");
    }

    private static boolean findClassByClassName(final String className) {
        boolean hasDependency;
        try {
            Class.forName(className);
            hasDependency = true;
        } catch (ClassNotFoundException e) {
            hasDependency = false;
        }
        return hasDependency;
    }

    public static void post(final Action action) {
        Completable.fromAction(action)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public static void post(final Scheduler scheduler, final Action action) {
        Completable.fromAction(action)
                .subscribeOn(scheduler)
                .subscribe();
    }
}