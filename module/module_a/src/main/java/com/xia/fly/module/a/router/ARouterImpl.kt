package com.xia.fly.module.a.router

import com.blankj.utilcode.util.BusUtils
import com.xia.fly.module.a.ui.AFragment
import com.xia.fly.module.common.router.BusContant

/**
 * @author weixia
 * @date 2019/3/15.
 */
@Suppress("unused")
object ARouterImpl {

    @BusUtils.Subscribe(name = BusContant.PAGE_A)
    fun obtainACls(): Class<*> {
        return AFragment::class.java
    }
}
