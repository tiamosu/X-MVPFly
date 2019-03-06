package com.xia.fly.demo.ui.fragments

import android.os.Bundle
import android.view.View
import com.xia.fly.demo.R
import com.xia.fly.demo.base.HeadViewFragment
import com.xia.fly.demo.mvp.presenter.HomePresenter
import com.xia.fly.demo.mvp.view.HomeView

/**
 * @author xia
 * @date 2018/8/6.
 */
class SecondFragment : HeadViewFragment<HomePresenter>(), HomeView {

    override fun getLayoutId(): Int {
        return R.layout.fragment_second
    }

    override fun newP(): HomePresenter {
        return HomePresenter()
    }

    override fun getBundleExtras(bundle: Bundle) {
//        val s = bundle.getString("Hello")
//        Log.e("weixi", "getBundleExtras: $s")
    }

    override fun initData() {}

    override fun initView() {}

    override fun initEvent() {
        if (view != null) {
            view!!.findViewById<View>(R.id.btn_jump)
                    .setOnClickListener { start(SecondFragment()) }
        }
    }

    override fun onLazyLoadData() {
        p.load(1)
        p.load(2)
        p.load(3)
        p.load(4)
        p.load(5)
    }

    override fun setData(content: String) {}

    override fun getBoolean(): Boolean {
        return false
    }

    override fun getNum(): Int {
        return 0
    }
}
