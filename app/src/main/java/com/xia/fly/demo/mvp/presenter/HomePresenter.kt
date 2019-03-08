package com.xia.fly.demo.mvp.presenter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.xia.fly.demo.mvp.view.HomeView
import com.xia.fly.http.RxHttp
import com.xia.fly.http.callback.AbstractStringCallback
import com.xia.fly.http.subscriber.CallbackSubscriber
import com.xia.fly.mvp.BaseMvpPresenter
import io.reactivex.disposables.Disposable

/**
 * @author xia
 * @date 2018/7/20.
 */
class HomePresenter : BaseMvpPresenter<HomeView>() {

    fun load(index: Int) {
//        Log.e("weixi", "load")
//        Log.e("weixi", "getView:$v")
//        Log.e("weixi", "boolean:${v.boolean}")
//        Log.e("weixi", "${v.num}")

        getV().setData("你好啊！！！")

        RxHttp.get("/friend/json")
                .build()
                .request(object : CallbackSubscriber(object : AbstractStringCallback(mLifecycleOwner) {
                    override fun onResponse(response: String?) {
                        Log.e("weixi", "onResponse$index :$response")
                    }
                }) {
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

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
//        Log.e("weixi", "onCreate: ")
    }
}
