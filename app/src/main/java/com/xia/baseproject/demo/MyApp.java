package com.xia.baseproject.demo;

import com.blankj.utilcode.util.ThreadUtils;
import com.xia.baseproject.BaseApp;

/**
 * @author xia
 * @date 2018/7/2.
 */
public class MyApp extends BaseApp {

    @Override
    public void onCreate() {
        if (!ThreadUtils.isMainThread()) {
            return;
        }
        super.onCreate();
    }
}
