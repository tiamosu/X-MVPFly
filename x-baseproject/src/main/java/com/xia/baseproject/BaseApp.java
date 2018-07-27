package com.xia.baseproject;

import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;

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
}
