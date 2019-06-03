package com.xia.fly.module.b.mvp.presenter

import android.util.Log
import com.xia.fly.http.RxHttp
import com.xia.fly.http.callback.AbstractStringCallback
import com.xia.fly.http.subscriber.CallbackSubscriber
import com.xia.fly.module.b.mvp.view.BView
import com.xia.fly.mvp.BaseMvpPresenter
import me.jessyan.rxerrorhandler.handler.RetryWithDelay

/**
 * @author weixia
 * @date 2019/3/15.
 */
class BPresenter : BaseMvpPresenter<BView>() {

    fun load(index: Int) {

        RxHttp["/friend/json"]
                .build()
                .request(object : CallbackSubscriber(object : AbstractStringCallback(mLifecycleOwner) {
                    override fun onResponse(response: String?) {
                        Log.e("weixi", "onResponse$index :$response")
                        v.setContent(response)
                    }
                }) {
                    override fun isShowLoadingDialog(): Boolean {
                        return true
                    }

                    override fun isGlobalErrorHandle(): Boolean {
                        return true
                    }

                    override fun getRetryWithDelay(): RetryWithDelay {
                        return RetryWithDelay(2, 2)
                    }
                })
    }
}
