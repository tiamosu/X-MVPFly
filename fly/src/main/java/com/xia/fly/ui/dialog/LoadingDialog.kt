package com.xia.fly.ui.dialog

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import com.wang.avi.AVLoadingIndicatorView
import com.xia.fly.R
import com.xia.fly.R2
import com.xia.fly.ui.dialog.loader.LoaderCreator
import com.xia.fly.ui.dialog.loader.LoaderStyles

/**
 * @author xia
 * @date 2018/7/29.
 */
class LoadingDialog @JvmOverloads constructor(
        context: Context, @LoaderStyles.LoaderStyle type: String = DEFAULT_LOADER,
        private var mMessage: String = "") : BaseDialog(context) {

    @BindView(R2.id.dialog_loading_iv)
    lateinit var mAVLoadingIndicatorView: AVLoadingIndicatorView
    @BindView(R2.id.dialog_loading_show_tv)
    lateinit var mLoadingTv: AppCompatTextView

    init {
        LoaderCreator.create(type, mAVLoadingIndicatorView)
    }

    override fun getLayoutId(): Int {
        return R.layout.base_dialog_loading
    }

    override fun initDialog() {
        setCanceledOnTouchOutside(false)
        window?.apply {
            setGravity(Gravity.CENTER)
            setDimAmount(0.2f)
        }
    }

    override fun show() {
        if (!TextUtils.isEmpty(mMessage)) {
            mLoadingTv.text = mMessage
            mLoadingTv.visibility = View.VISIBLE
        }
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
