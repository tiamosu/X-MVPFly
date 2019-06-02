package com.xia.fly.standalone.a

import com.xia.fly.module.a.ui.AFragment
import com.xia.fly.ui.activities.ProxyActivity
import com.xia.fly.ui.fragments.FlySupportFragment

/**
 * @author weixia
 */
class MainActivity : ProxyActivity() {

    override fun setRootFragment(): Class<out FlySupportFragment<*>> {
        return AFragment::class.java
    }
}
