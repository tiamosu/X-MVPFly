package com.xia.baseproject.rxhttp.utils;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

/**
 * @author xia
 * @date 2018/4/24.
 */
public class Platform {
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

    @SuppressWarnings("unchecked")
    public static void post(final Action action) {
        Completable.fromAction(action)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
