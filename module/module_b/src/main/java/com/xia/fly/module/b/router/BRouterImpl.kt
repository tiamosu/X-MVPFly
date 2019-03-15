package com.xia.fly.module.b.router

import com.blankj.utilcode.util.BusUtils
import com.xia.fly.module.b.ui.BFragment
import com.xia.fly.module.common.router.BusContant

/**
 * @author weixia
 * @date 2019/3/15.
 */
@Suppress("unused")
object BRouterImpl {

    @BusUtils.Subscribe(name = BusContant.PAGE_B)
    fun obtainBCls(): Class<*> {
        return BFragment::class.java
    }
}
