package com.xia.fly.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.annotation.StyleRes
import butterknife.ButterKnife
import com.blankj.utilcode.util.AntiShakeUtils
import com.blankj.utilcode.util.ScreenUtils
import com.xia.fly.R

/**
 * @author xia
 * @date 2018/7/29.
 */
abstract class BaseDialog @JvmOverloads constructor(
        context: Context, @StyleRes themeResId: Int = R.style.baseDialogStyle)
    : Dialog(context, themeResId), View.OnClickListener {

    protected abstract fun getLayoutId(): Int

    protected open fun onWidgetClick(view: View) {}

    init {
        apply {
            val view = View.inflate(context, getLayoutId(), null)
            setContentView(view)
            ButterKnife.bind(this, view)

            initDialog()
        }
    }

    protected open fun initDialog() {
        setCanceledOnTouchOutside(false)
        window?.apply {
            val params = attributes
            params.width = (ScreenUtils.getScreenWidth() * 0.8).toInt()
            //dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗。
            //如果要达到背景全部变暗的效果，需要设置  dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            //否则，背景无效果。
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            attributes = params
            //这个函数用来设置 Dialog 周围的颜色。系统默认的是半透明的灰色。值设为0则为完全透明。
            setDimAmount(0.2f)
            setGravity(Gravity.CENTER)
            setWindowAnimations(R.style.dialogEmptyAnimation)
        }
    }

    override fun onClick(view: View?) {
        view ?: return
        if (AntiShakeUtils.isValid(view)) {
            onWidgetClick(view)
        }
    }

    companion object {

        @JvmStatic
        fun safeShowDialog(dialog: Dialog?) {
            try {
                if (dialog?.isShowing == false) {
                    dialog.show()
                }
            } catch (ignored: Exception) {
            }
        }

        @JvmStatic
        fun safeCloseDialog(dialog: Dialog?) {
            try {
                if (dialog?.isShowing == true) {
                    dialog.cancel()
                }
            } catch (ignored: Exception) {
            }
        }
    }
}
