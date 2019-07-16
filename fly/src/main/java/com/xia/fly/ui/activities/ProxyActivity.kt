package com.xia.fly.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.widget.ContentFrameLayout
import com.xia.fly.R
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.utils.FragmentUtils
import com.xia.fly.utils.Preconditions
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author xia
 * @date 2018/7/3.
 */
@Suppress("unused")
abstract class ProxyActivity<P : BaseMvpPresenter<*>> : FlySupportActivity<P>() {

    private var mContainerLayout: ContentFrameLayout? = null

    fun getContainerLayout(): ContentFrameLayout? {
        return mContainerLayout
    }

    /**
     * @return APP被杀死重启时，是否还原到被杀死前保存的状态
     */
    open fun isRestartRestore(): Boolean {
        return true
    }

    /**
     * @return 设置根Fragment
     */
    protected abstract fun setRootFragment(): Class<out ISupportFragment>?

    override fun newP(): P? {
        return null
    }

    override fun getLayoutId(): Int {
        return 0
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isRestartRestore() && savedInstanceState != null) {
            finish()
            return
        }

        val containerId = R.id.delegate_container
        if (getLayoutId() == 0) {
            mContainerLayout = ContentFrameLayout(getContext())
            mContainerLayout!!.id = containerId
            setContentView(mContainerLayout)
        }

        loadProxyRootFragment(containerId)
        initMvp()
        initData()
        initView()
        initEvent()
        onLazyLoadData()
    }

    @CallSuper
    protected open fun loadProxyRootFragment(proxyContainerId: Int) {
        Preconditions.checkNotNull<Any>(setRootFragment(),
                "you must set the rootFragment not be null!")

        if (findFragment(setRootFragment()) == null) {
            if (getLayoutId() != 0 && proxyContainerId == R.id.delegate_container) {
                Preconditions.checkArgument(false,
                        "you should override loadProxyRootFragment(proxyContainerId)!")
            }

            val toFragment: ISupportFragment? = FragmentUtils.newInstance(setRootFragment())
            loadRootFragment(proxyContainerId, toFragment)
        }
    }

    override fun initData() {}

    override fun initView() {}

    override fun initEvent() {}

    override fun onLazyLoadData() {}
}
