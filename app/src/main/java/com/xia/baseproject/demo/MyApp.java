package com.xia.baseproject.demo;

import com.blankj.utilcode.util.ThreadUtils;
import com.xia.baseproject.BaseApp;
import com.xia.baseproject.rxhttp.config.Rest;

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

        Rest.init()
                .withApiHost("http://www.wanandroid.com")
                .config();
    }
}
