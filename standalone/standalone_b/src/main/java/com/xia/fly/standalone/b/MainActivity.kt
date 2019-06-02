package com.xia.fly.standalone.b

import com.xia.fly.module.b.ui.BFragment
import com.xia.fly.ui.activities.ProxyActivity
import com.xia.fly.ui.fragments.FlySupportFragment

/**
 * @author weixia
 */
class MainActivity : ProxyActivity() {

    override fun setRootFragment(): Class<out FlySupportFragment<*>> {
        return BFragment::class.java
    }
}
