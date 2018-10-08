package com.xia.fly.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * @author xia
 * @date 2018/10/8.
 */
public final class SystemUtils {

    private SystemUtils() {
        throw new IllegalStateException("u can't instantiate me!");
    }

    private static String PROCESS_NAME;

    /**
     * 判断当前进程是否主进程
     */
    public static boolean isInMainProcess(Context context) {
        final String pName = getProcessName(context);
        return !TextUtils.isEmpty(pName) && pName.equals(context.getPackageName());
    }

    /**
     * 获取当前进程名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        if (!TextUtils.isEmpty(PROCESS_NAME)) {
            return PROCESS_NAME;
        }
        try {
            final int pid = android.os.Process.myPid();
            final ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager == null) {
                return null;
            }
            final List<ActivityManager.RunningAppProcessInfo> appProcessList = manager.getRunningAppProcesses();
            if (appProcessList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : appProcessList) {
                    if (processInfo.pid == pid) {
                        PROCESS_NAME = processInfo.processName;
                        return PROCESS_NAME;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
