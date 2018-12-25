package com.xia.fly.base;

import com.xia.fly.di.component.AppComponent;

import androidx.annotation.NonNull;

/**
 * 框架要求框架中的每个 {@link android.app.Application} 都需要实现此类, 以满足规范
 *
 * @author xia
 * @date 2018/9/14.
 */
public interface App {

    @NonNull
    AppComponent getAppComponent();
}
