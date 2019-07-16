package com.xia.fly.module.b.ui

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.xia.fly.module.b.R
import com.xia.fly.module.b.mvp.presenter.BPresenter
import com.xia.fly.module.b.mvp.view.BView
import com.xia.fly.module.common.base.BaseFragment

/**
 * @author weixia
 * @date 2019/3/15.
 */
class BFragment : BaseFragment<BPresenter>(), BView {

    override fun isLoadTitleBar(): Boolean {
        return true
    }

    override fun newP(): BPresenter {
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
        applyWidgetClickListener(getView(viewId = R.id.btn_jump))
    }

    override fun onLazyLoadData() {
//        //mvp动态代理测试
//        p.detachView()
//        Handler().postDelayed({ p.load(1) }, 1000)

        p.load(0)
    }

    override fun setContent(content: String?) {
        getView<AppCompatTextView>(viewId = R.id.tv_content)?.text = content ?: ""
    }

    override val boolean: Boolean
        get() = true

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_jump -> {
                start(BFragment())
            }
        }
    }
}
