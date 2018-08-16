package com.xia.baseproject.demo.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest;
import com.blankj.utilcode.util.Utils;

/**
 * @author xia
 */
public class DialogHelper {
    public static final int APP_SETTINGS_CODE = 2;

    public static void showRationaleDialog(Context context, ShouldRequest shouldRequest) {
        new AlertDialog.Builder(context)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("再次请求权限")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> shouldRequest.again(true))
//                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        shouldRequest.again(false);
//                    }
//                })
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showOpenAppSettingDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("前往设置开启相关权限")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    final Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((Activity) context).startActivityForResult(intent, APP_SETTINGS_CODE);
                })
//                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
                .setCancelable(false)
                .create()
                .show();
    }
}
