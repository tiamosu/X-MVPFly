package com.xia.fly.module.b.mvp.presenter

import android.util.Log
import com.xia.fly.http.RxHttp
import com.xia.fly.http.callback.GenericsCallback
import com.xia.fly.http.converter.GsonConverter
import com.xia.fly.http.subscriber.CallbackSubscriber
import com.xia.fly.module.b.mvp.view.BView
import com.xia.fly.module.b.result.Friend
import com.xia.fly.mvp.BaseMvpPresenter

/**
 * @author weixia
 * @date 2019/3/15.
 */
class BPresenter : BaseMvpPresenter<BView>() {

    fun load(index: Int) {
//        RxHttp["/friend/json"]
//                .build()
//                .request(object : CallbackSubscriber(object : AbstractStringCallback(mLifecycleOwner) {
//                    override fun onResponse(response: String?) {
//                        Log.e("susu", "onResponse$index :$response")
//                        v.setContent(response)
//                    }
//                }) {
//                    override fun isShowLoadingDialog(): Boolean {
//                        return true
//                    }
//
//                    override fun isGlobalErrorHandle(): Boolean {
//                        return true
//                    }
//
//                    override fun getRetryWithDelay(): RetryWithDelay {
//                        return RetryWithDelay(2, 2)
//                    }
//                })

        RxHttp["/friend/json"]
                .build()
                .request(object : CallbackSubscriber(object : GenericsCallback<Friend>(mLifecycleOwner, GsonConverter()) {
                    override fun onResponse(response: Friend?) {
                        val data = response?.getData()
                        if (data?.isNotEmpty() == true) {
                            val result = "tag:$index   \ndata:${data[0]}"
                            Log.e("susu", result)

                            val bol = getV().boolean
                            Log.e("susu", "bol:$bol")

                            getV().setContent(result)
                        }
                    }
                }) {})
    }
}
