package com.xia.fly.ui.dialog

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.wang.avi.AVLoadingIndicatorView
import com.xia.fly.R
import com.xia.fly.ui.dialog.loader.LoaderCreator
import com.xia.fly.ui.dialog.loader.LoaderStyles

/**
 * @author xia
 * @date 2018/7/29.
 */
class LoadingDialog constructor(context: Context) : FlySupportDialog(context) {
    private lateinit var mAVLoadingIndicatorView: AVLoadingIndicatorView
    private lateinit var mLoadingTv: AppCompatTextView

    override fun getLayoutId(): Int {
        return R.layout.base_dialog_loading
    }

    override fun onBindAny(view: View) {
    }

    override fun initDialog() {
        setCanceledOnTouchOutside(false)
        window?.apply {
            setGravity(Gravity.CENTER)
            setDimAmount(0.2f)
        }

        mAVLoadingIndicatorView = findViewById(R.id.dialog_loading_iv)
        mLoadingTv = findViewById(R.id.dialog_loading_show_tv)
    }

    fun setMessage(message: String?) {
        if (!TextUtils.isEmpty(message)) {
            mLoadingTv.text = message
            mLoadingTv.visibility = View.VISIBLE
        }
    }

    fun setType(@LoaderStyles.LoaderStyle type: String?) {
        val typeTemp = type ?: DEFAULT_LOADER
        LoaderCreator.create(typeTemp, mAVLoadingIndicatorView)
    }

    override fun show() {
        mAVLoadingIndicatorView.smoothToShow()
        super.show()
    }

    override fun cancel() {
        mAVLoadingIndicatorView.smoothToHide()
        super.cancel()
    }

    companion object {

        private const val DEFAULT_LOADER = LoaderStyles.LINE_SPIN_FADE_LOADER_INDICATOR
    }
}
