package com.xia.fly.ui.dialog.loader

import android.app.Dialog
import com.xia.fly.ui.dialog.BaseDialog
import java.util.concurrent.ConcurrentHashMap

/**
 * @author xia
 * @date 2018/8/21.
 */
object Loader {
    private val LOADERS = ConcurrentHashMap<Any, Dialog>()

    @JvmStatic
    fun showLoading(dialog: Dialog?) {
        showLoading("", dialog)
    }

    @JvmStatic
    fun showLoading(subscriber: Any?, dialog: Dialog?) {
        if (subscriber != null && dialog != null) {
            synchronized(LOADERS) {
                LOADERS[subscriber] = dialog
                BaseDialog.safeShowDialog(dialog)
            }
        }
    }

    @JvmStatic
    fun stopLoading(subscriber: Any?) {
        subscriber ?: return
        synchronized(LOADERS) {
            for (dialog in LOADERS) {
                if (dialog.key == subscriber) {
                    BaseDialog.safeCloseDialog(dialog.value)
                }
            }
        }
    }

    @JvmStatic
    fun stopLoading() {
        synchronized(LOADERS) {
            for (dialog in LOADERS) {
                BaseDialog.safeCloseDialog(dialog.value)
            }
            LOADERS.clear()
        }
    }
}
