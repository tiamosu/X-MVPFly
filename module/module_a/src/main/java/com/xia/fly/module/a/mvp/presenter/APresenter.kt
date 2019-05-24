package com.xia.fly.module.a.mvp.presenter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.xia.fly.module.a.mvp.view.AView
import com.xia.fly.mvp.BaseMvpPresenter

/**
 * @author weixia
 * @date 2019/3/15.
 */
class APresenter : BaseMvpPresenter<AView>() {

    fun load(index: Int) {
        Log.e("weixi", "load")
        Log.e("weixi", "getView:$v")
        Log.e("weixi", "boolean:${v.boolean}")
        Log.e("weixi", "${v.num}")

        v.setData("你好啊！！！")
    }

    fun test() {
    }

    @Suppress("RedundantOverride")
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
//        Log.e("weixi", "onCreate: ")
    }
}
