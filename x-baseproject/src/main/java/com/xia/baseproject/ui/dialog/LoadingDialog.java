package com.xia.baseproject.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.wang.avi.AVLoadingIndicatorView;
import com.xia.baseproject.R;
import com.xia.baseproject.R2;
import com.xia.baseproject.ui.dialog.load.LoaderCreator;
import com.xia.baseproject.ui.dialog.load.LoaderStyles;

import butterknife.BindView;

/**
 * @author xia
 * @date 2018/7/29.
 */
@SuppressWarnings("WeakerAccess")
public class LoadingDialog extends BaseDialog {
    @BindView(R2.id.dialog_loading_iv)
    AVLoadingIndicatorView mAVLoadingIndicatorView;
    @BindView(R2.id.dialog_loading_show_tv)
    AppCompatTextView mLoadingTv;

    private static final String DEFAULT_LOADER = LoaderStyles.LineSpinFadeLoaderIndicator;

    private String mMessage;

    public LoadingDialog(@NonNull Context context) {
        this(context, "");
    }

    public LoadingDialog(@NonNull Context context, String message) {
        this(context, DEFAULT_LOADER, message);
    }

    public LoadingDialog(@NonNull Context context, @LoaderStyles.LoaderStyle String type, String message) {
        super(context);
        this.mMessage = message;
        LoaderCreator.create(type, mAVLoadingIndicatorView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_dialog_loading;
    }

    @Override
    protected void initDialog() {
        final Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setDimAmount(0.2f);
            setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void show() {
        super.show();
        if (!TextUtils.isEmpty(mMessage)) {
            mLoadingTv.setText(mMessage);
            mLoadingTv.setVisibility(View.VISIBLE);
        }
        mAVLoadingIndicatorView.show();
    }

    @Override
    public void cancel() {
        super.cancel();
        mAVLoadingIndicatorView.hide();
    }
}
