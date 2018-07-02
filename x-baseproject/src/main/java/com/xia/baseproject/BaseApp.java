package com.xia.baseproject;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * @author xia
 * @date 2018/7/2.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
