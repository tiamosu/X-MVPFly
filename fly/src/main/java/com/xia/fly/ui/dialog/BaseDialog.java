package com.xia.fly.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.xia.fly.R;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import butterknife.ButterKnife;

/**
 * @author xia
 * @date 2018/7/29.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseDialog extends Dialog {

    protected abstract int getLayoutId();

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.baseDialogStyle);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        final View view = View.inflate(context, getLayoutId(), null);
        setContentView(view);
        ButterKnife.bind(this, view);

        initDialog();
    }

    protected void initDialog() {
        setCanceledOnTouchOutside(false);
        final Window window = getWindow();
        if (window != null) {
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
            //dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗。
            //如果要达到背景全部变暗的效果，需要设置  dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            //否则，背景无效果。
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);
            //这个函数用来设置 Dialog 周围的颜色。系统默认的是半透明的灰色。值设为0则为完全透明。
            window.setDimAmount(0.2f);
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.dialogEmptyAnimation);
        }
    }

    public static void safeShowDialog(Dialog dialog) {
        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception ignored) {
        }
    }

    public static void safeCloseDialog(Dialog dialog) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.cancel();
            }
        } catch (Exception ignored) {
        }
    }
}