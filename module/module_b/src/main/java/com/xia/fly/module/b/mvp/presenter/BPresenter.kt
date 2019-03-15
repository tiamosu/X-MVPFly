package com.xia.fly.module.b.mvp.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.xia.fly.http.RxHttp
import com.xia.fly.http.callback.AbstractStringCallback
import com.xia.fly.http.subscriber.CallbackSubscriber
import com.xia.fly.module.b.mvp.view.BView
import com.xia.fly.mvp.BaseMvpPresenter
import io.reactivex.disposables.Disposable

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
                        getV().setContent(response)
                    }
                }) {
                    @SuppressLint("MissingPermission")
                    override fun onSubscribe(d: Disposable) {
                        if (!NetworkUtils.isConnected()) {
                            d.dispose()
                            ToastUtils.showShort("无法连接网络")
                            return
                        }
                        super.onSubscribe(d)
                    }
                })
    }
}
