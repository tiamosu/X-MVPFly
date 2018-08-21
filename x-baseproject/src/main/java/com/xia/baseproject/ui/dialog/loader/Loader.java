package com.xia.baseproject.ui.dialog.loader;

import android.app.Dialog;

import com.xia.baseproject.ui.dialog.BaseDialog;

import java.util.ArrayList;

/**
 * @author xia
 * @date 2018/8/21.
 */
public final class Loader {
    private static final ArrayList<Dialog> LOADERS = new ArrayList<>();

    public static void showLoading(final Dialog dialog) {
        if (dialog != null) {
            LOADERS.add(dialog);
            BaseDialog.safeShowDialog(dialog);
        }
    }

    public static void stopLoading() {
        for (Dialog dialog : LOADERS) {
            BaseDialog.safeCloseDialog(dialog);
        }
        LOADERS.clear();
    }
}
