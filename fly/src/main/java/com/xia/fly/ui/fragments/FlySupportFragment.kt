package com.xia.fly.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.CacheType
import com.xia.fly.integration.rxbus.IRxBusCallback
import com.xia.fly.integration.rxbus.RxBusHelper
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.mvp.BaseMvpView
import com.xia.fly.ui.fragments.delegate.FlySupportFragmentDelegate
import com.xia.fly.utils.FlyUtils
import me.yokeyword.fragmentation.SupportFragment
import java.lang.ref.WeakReference

/**
 * @author xia
 * @date 2018/8/1.
 */
abstract class FlySupportFragment<out P : BaseMvpPresenter<BaseMvpView<P>>> : SupportFragment(), IFragment, BaseMvpView<P> {
    private val mDelegate = FlySupportFragmentDelegate(apply { })
    private var mPresenter: P? = null
    private var mCache: Cache<String, Any>? = null
    private var mRootView: WeakReference<View?>? = null

    protected val rootView: View?
        get() = mRootView?.get()

    protected val p: P?
        get() = mPresenter

    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.FRAGMENT_CACHE)
        }
        return mCache!!
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : SupportFragment> getParentDelegate(): T {
        val parentFragment = parentFragment
        return (parentFragment ?: this) as T
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null || rootView == null) {
            val view = mDelegate.onCreateView(inflater, container)
            mRootView = WeakReference(view)
        } else {
            // 缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            val viewParent = rootView?.parent
            if (viewParent is ViewGroup) {
                viewParent.removeView(rootView)
            }
        }
        return rootView
    }

    /**
     * 在[.loadMultipleRootFragment]的情况下，
     * 该方法将会先于{[.onSupportVisible]先执行
     */
    @CallSuper
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        mDelegate.onEnterAnimationEnd()
    }

    @CallSuper
    override fun onSupportVisible() {
        mDelegate.onSupportVisible()
    }

    @CallSuper
    override fun onNewBundle(args: Bundle) {
        mDelegate.getBundle(args)
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

    override fun onLazyLoadData() {}

    override fun isLoadTitleBar(): Boolean {
        return true
    }

    override fun onCreateTitleBar(titleBarContainer: FrameLayout) {}

    override fun getBundleExtras(bundle: Bundle) {}

    @CallSuper
    override fun onVisibleLazyLoad() {
    }

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
