package com.xia.fly.http.request

import com.uber.autodispose.ObservableSubscribeProxy
import com.xia.fly.http.subscriber.CallbackSubscriber
import com.xia.fly.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import okhttp3.ResponseBody

/**
 * @author xia
 * @date 2018/7/27.
 */
class RequestCall(private val mObservable: Observable<ResponseBody>?) {

    fun request(subscriber: CallbackSubscriber) {
        mObservable?.apply {
            val callback = subscriber.mCallback ?: return

            subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔时间（单位：秒）
                    .retryWhen(RetryWithDelay(2, 2))
                    .`as`<ObservableSubscribeProxy<ResponseBody>>(RxLifecycleUtils.bindLifecycle(callback.getLifecycleOwner()))
                    .subscribe(subscriber)
        }
    }
}
