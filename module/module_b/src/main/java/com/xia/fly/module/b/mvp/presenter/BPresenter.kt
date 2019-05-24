package com.xia.fly.module.b.mvp.presenter

import android.util.Log
import com.xia.fly.http.RxHttp
import com.xia.fly.http.callback.AbstractStringCallback
import com.xia.fly.http.subscriber.CallbackSubscriber
import com.xia.fly.module.b.mvp.view.BView
import com.xia.fly.mvp.BaseMvpPresenter

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
                }) {})
    }
}
