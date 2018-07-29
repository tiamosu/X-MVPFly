package com.xia.baseproject.utils;

import android.app.Dialog;

/**
 * @author xia
 * @date 2018/7/29.
 */
public class DialogUtils {

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
