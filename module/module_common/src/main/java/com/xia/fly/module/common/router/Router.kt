package com.xia.fly.module.common.router

import com.blankj.utilcode.util.BusUtils
import com.xia.fly.module.common.ui.EmptyFragment

import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author weixia
 * @date 2019/3/15.
 */
object Router {

    fun obtainACls(): Class<out ISupportFragment> {
        val aClass = BusUtils.post<Class<*>?>(BusContant.PAGE_A)
        return obtainCls(aClass)
    }

    fun obtainBCls(): Class<out ISupportFragment> {
        val aClass = BusUtils.post<Class<*>?>(BusContant.PAGE_B)
        return obtainCls(aClass)
    }

    @Suppress("UNCHECKED_CAST")
    private fun obtainCls(cls: Class<*>?): Class<out ISupportFragment> {
        return cls as Class<out ISupportFragment>? ?: EmptyFragment::class.java
    }
}
