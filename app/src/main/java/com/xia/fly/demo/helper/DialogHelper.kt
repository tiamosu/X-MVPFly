package com.xia.fly.demo.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest
import com.blankj.utilcode.util.Utils

/**
 * @author xia
 */
object DialogHelper {
    const val APP_SETTINGS_CODE = 2

    @JvmStatic
    fun showRationaleDialog(context: Context, shouldRequest: ShouldRequest) {
        AlertDialog.Builder(context)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("再次请求权限")
                .setPositiveButton(android.R.string.ok) { dialog, which -> shouldRequest.again(true) }
//                .setNegativeButton(android.R.string.cancel) { dialog, whick ->
//                    shouldRequest.again(false)
//                }
                .setCancelable(false)
                .create()
                .show()
    }

    @JvmStatic
    fun showOpenAppSettingDialog(context: Context) {
        AlertDialog.Builder(context)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("前往设置开启相关权限")
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
                    intent.data = Uri.parse("package:" + Utils.getApp().packageName)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    (context as Activity).startActivityForResult(intent, APP_SETTINGS_CODE)
                }
//                .setNegativeButton(android.R.string.cancel) { dialog, which -> }
                .setCancelable(false)
                .create()
                .show()
    }
}
