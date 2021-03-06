package com.xia.fly.http.subscriber

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import com.xia.fly.http.callback.Callback
import com.xia.fly.ui.dialog.LoadingDialog
import com.xia.fly.ui.dialog.loader.Loader
import com.xia.fly.utils.FlyUtils
import com.xia.fly.utils.Platform
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import okhttp3.ResponseBody

/**
 * @author xia
 * @date 2018/8/3.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class CallbackSubscriber(var mCallback: Callback<*>?)
    : ErrorHandleSubscriber<ResponseBody>(FlyUtils.getAppComponent().rxErrorHandler()) {

    private var mDisposable: Disposable? = null

    /**
     * 是否网络数据请求时，显示加载框
     */
    protected open fun isShowLoadingDialog(): Boolean {
        return true
    }

    /**
     * 是否进行全局错误统一处理
     */
    protected open fun isGlobalErrorHandle(): Boolean {
        return true
    }

    /**
     * 遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔时间（单位：秒）
     */
    @NonNull
    open fun getRetryWithDelay(): RetryWithDelay {
        return RetryWithDelay(2, 2)
    }

    protected open fun getLoadingDialog(): Dialog? {
        val context: Context? = mCallback?.getContext()
        return if (context == null) null else LoadingDialog(context)
    }

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
        if (!isDisposed()) {
            Platform.post(Action {
                showDialog()
                mCallback?.onSubscribe(d)
            })
        }
    }

    override fun onNext(responseBody: ResponseBody) {
        if (!isDisposed()) {
            mCallback?.parseNetworkResponse(responseBody)
        }
    }

    override fun onError(e: Throwable) {
        if (isGlobalErrorHandle()) {
            super.onError(e)
        }
        Platform.post(Action {
            mCallback?.onError(e)
            mCallback = null
            cancelDialog()
        })
    }

    override fun onComplete() {
        Platform.post(Action {
            mCallback?.onComplete()
            mCallback = null
            cancelDialog()
        })
    }

    protected fun showDialog() {
        if (isShowLoadingDialog() && isPageVisible()) {
            LOADING_HANDLER.postDelayed({
                Loader.showLoading(getLoadingDialog())
            }, 300)
        }
    }

    protected fun cancelDialog() {
        if (isShowLoadingDialog()) {
            LOADING_HANDLER.removeCallbacksAndMessages(null)
            Loader.stopLoading()
        }
        if (!isDisposed()) {
            dispose()
        }
    }

    private fun isPageVisible(): Boolean {
        return if (mCallback != null) {
            FlyUtils.isCurrentVisible(mCallback!!.getLifecycleOwner())
        } else false
    }

    protected fun isDisposed(): Boolean {
        return mDisposable?.isDisposed == true
    }

    protected fun dispose() {
        mDisposable?.dispose()
    }

    companion object {

        private val LOADING_HANDLER = Handler(Looper.getMainLooper())
    }
}
