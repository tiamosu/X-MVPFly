package com.xia.fly.base.delegate

import android.app.Activity
import android.os.Bundle

/**
 * [ActivityDelegate] 默认实现类
 *
 * @author xia
 * @date 2018/9/20.
 */
@Suppress("UNUSED_PARAMETER")
class ActivityDelegateImpl(mActivity: Activity) : ActivityDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {}

    override fun onSaveInstanceState(outState: Bundle) {}

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onDestroy() {}
}
