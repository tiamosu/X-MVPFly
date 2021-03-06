package com.xia.fly.utils

import android.os.Handler
import android.os.Looper

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action

/**
 * @author xia
 * @date 2018/4/24.
 */
object Platform {

    @JvmStatic
    fun getHandler() = Handler(Looper.getMainLooper())

    @JvmStatic
    fun post(action: Action) {
        Completable.fromAction(action)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    @JvmStatic
    fun post(scheduler: Scheduler, action: Action) {
        Completable.fromAction(action)
                .subscribeOn(scheduler)
                .subscribe()
    }
}
