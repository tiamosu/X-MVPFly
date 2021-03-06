package com.xia.fly.base

import com.xia.fly.di.component.AppComponent

/**
 * 框架要求框架中的每个 [android.app.Application] 都需要实现此类, 以满足规范
 *
 * @author xia
 * @date 2018/9/14.
 */
interface App {

    fun getAppComponent(): AppComponent
}
