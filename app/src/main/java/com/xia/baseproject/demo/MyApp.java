package com.xia.baseproject.demo;

import com.blankj.utilcode.util.ThreadUtils;
import com.xia.baseproject.BaseApp;
import com.xia.baseproject.app.Rest;

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

        Rest.init(this)
                .withApiHost("http://www.wanandroid.com")
                .config();
    }
}
