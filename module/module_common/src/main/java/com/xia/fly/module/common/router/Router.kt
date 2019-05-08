package com.xia.fly.module.common.router

import com.alibaba.android.arouter.launcher.ARouter
import com.xia.fly.module.common.ui.EmptyFragment
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author weixia
 * @date 2019/3/15.
 */
object Router {

    fun obtainFragmentACls(): Class<out ISupportFragment> {
        return obtainFragmentCls(getProviderA().obtainACls())
    }

    fun obtainFragmentBCls(): Class<out ISupportFragment> {
        return obtainFragmentCls(getProviderB().obtainBCls())
    }

    private fun getProviderA(): IProviderA {
        return ARouter.getInstance()
                .build(RouterConstant.PROVIDER_A).navigation() as IProviderA
    }

    private fun getProviderB(): IProviderB {
        return ARouter.getInstance()
                .build(RouterConstant.PROVIDER_B).navigation() as IProviderB
    }

    private fun obtainFragmentCls(cls: Class<out ISupportFragment>?): Class<out ISupportFragment> {
        return cls ?: EmptyFragment::class.java
    }
}
