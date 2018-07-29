package com.xia.baseproject.ui.loder;

import android.app.Dialog;

import com.xia.baseproject.ui.dialog.BaseDialog;
import com.xia.baseproject.ui.dialog.LoadingDialog;
import com.xia.baseproject.ui.loder.LoaderStyles.LoaderStyle;

import java.util.ArrayList;

/**
 * @author xia
 * @date 2018/7/29.
 */
@SuppressWarnings("WeakerAccess")
public final class Loader {
    private static final ArrayList<Dialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStyles.LineSpinFadeLoaderIndicator;

    public static void showLoading(Dialog dialog) {
        showLoading(dialog, DEFAULT_LOADER);
    }

    public static void showLoading(Dialog dialog, @LoaderStyle String type) {
        if (dialog == null) {
            return;
        }
        if (dialog instanceof LoadingDialog) {
            LoaderCreator.create(type, ((LoadingDialog) dialog).getAVLoadingIndicatorView());
        }
        LOADERS.add(dialog);
        BaseDialog.safeShowDialog(dialog);
    }

    public static void stopLoading() {
        for (Dialog dialog : LOADERS) {
            BaseDialog.safeCloseDialog(dialog);
        }
        LOADERS.clear();
    }
}
