package com.xia.fly.ui.activities

import android.os.Bundle
import android.widget.FrameLayout

import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.CacheType
import com.xia.fly.integration.rxbus.IRxBusCallback
import com.xia.fly.integration.rxbus.RxBusHelper
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.mvp.BaseMvpView
import com.xia.fly.ui.activities.delegate.FlySupportActivityDelegate
import com.xia.fly.utils.FlyUtils
import me.yokeyword.fragmentation.SupportActivity

/**
 * @author xia
 * @date 2018/8/16.
 */
abstract class FlySupportActivity<P : BaseMvpPresenter<BaseMvpView<P>>> : SupportActivity(), IActivity, BaseMvpView<P> {
    private val mDelegate = FlySupportActivityDelegate(apply { })
    private var mPresenter: P? = null
    private var mCache: Cache<String, Any>? = null

    protected val p: P?
        get() = mPresenter

    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.ACTIVITY_CACHE)
        }
        return mCache!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mDelegate.onResume()
    }

    override fun initMvp() {
        if (mPresenter == null) {
            mPresenter = newP()
            if (mPresenter != null) {
                mPresenter!!.attachView(this)
                lifecycle.addObserver(mPresenter!!)
            }
        }
    }

    override fun onDestroy() {
        mDelegate.onDestroy<BaseMvpPresenter<*>>(mPresenter)
        super.onDestroy()
    }

    override fun isLoadTitleBar(): Boolean {
        return false
    }

    override fun onCreateTitleBar(titleBarContainer: FrameLayout) {}

    override fun isCheckNetWork(): Boolean {
        return true
    }

    override fun onNetworkState(isAvailable: Boolean) {}

    override fun onNetReConnect() {}

    protected fun subscribeWithTags(callback: IRxBusCallback, vararg tags: String) {
        RxBusHelper.subscribeWithTags(this, callback, *tags)
    }

    protected fun subscribeStickyWithTags(callback: IRxBusCallback, vararg tags: String) {
        RxBusHelper.subscribeStickyWithTags(this, callback, *tags)
    }
}
