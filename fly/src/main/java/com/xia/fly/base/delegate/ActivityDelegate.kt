package com.xia.fly.base.delegate

import android.app.Activity
import android.os.Bundle

/**
 * [Activity] 代理类,用于框架内部在每个 [Activity] 的对应生命周期中插入需要的逻辑
 *
 * @author xia
 * @date 2018/9/20.
 * @see ActivityDelegateImpl
 *
 * @see [ActivityDelegate wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.3.13)
 */
interface ActivityDelegate {

    fun onCreate(savedInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    companion object {

        const val ACTIVITY_DELEGATE = "ACTIVITY_DELEGATE"
    }
}
