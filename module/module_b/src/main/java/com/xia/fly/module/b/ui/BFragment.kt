package com.xia.fly.module.b.ui

import android.view.View
import com.xia.fly.module.b.R
import com.xia.fly.module.b.mvp.presenter.BPresenter
import com.xia.fly.module.b.mvp.view.BView
import com.xia.fly.module.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_b.*

/**
 * @author weixia
 * @date 2019/3/15.
 */
class BFragment : BaseFragment<BPresenter>(), BView {

    override fun isLoadTitleBar(): Boolean {
        return true
    }

    override fun newP(): BPresenter? {
        return BPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_b
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
        view?.findViewById<View>(R.id.btn_jump)
                ?.setOnClickListener { start(BFragment()) }
    }

    override fun onLazyLoadData() {
        p?.load(1)
//        p.load(2)
//        p.load(3)
//        p.load(4)
//        p.load(5)
    }

    override fun setContent(content: String?) {
        tv_content.text = content ?: ""
    }
}
