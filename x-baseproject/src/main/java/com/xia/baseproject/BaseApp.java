package com.xia.baseproject;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;

import java.util.List;

/**
 * @author xia
 * @date 2018/7/2.
 */
public class BaseApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

    protected boolean isMainThread() {
        final ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfoList = null;
        if (am != null) {
            processInfoList = am.getRunningAppProcesses();
        }
        final String mainProcessName = getPackageName();
        final int myPid = android.os.Process.myPid();
        if (processInfoList != null) {
            for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
