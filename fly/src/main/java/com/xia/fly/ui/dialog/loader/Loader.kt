package com.xia.fly.ui.dialog.loader

import android.app.Dialog
import com.xia.fly.ui.dialog.FlySupportDialog
import java.util.*

/**
 * @author xia
 * @date 2018/8/21.
 */
object Loader {
    private val LOADERS = ArrayList<Dialog>()

    @JvmStatic
    fun showLoading(dialog: Dialog?) {
        if (dialog != null) {
            LOADERS.add(dialog)
            FlySupportDialog.safeShowDialog(dialog)
        }
    }

    @JvmStatic
    fun stopLoading() {
        for (dialog in LOADERS) {
            FlySupportDialog.safeCloseDialog(dialog)
        }
        LOADERS.clear()
    }
}
