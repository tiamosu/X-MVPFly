package com.xia.fly.module.b.router

import android.content.Context

import com.alibaba.android.arouter.facade.annotation.Route
import com.xia.fly.module.b.ui.BFragment
import com.xia.fly.module.common.router.IProviderB
import com.xia.fly.module.common.router.RouterConstant
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author weixia
 * @date 2019/5/8.
 */
@Route(path = RouterConstant.PROVIDER_B)
class IProviderBImpl : IProviderB {

    override fun obtainBCls(): Class<out ISupportFragment> {
        return BFragment::class.java
    }

    override fun init(context: Context) {}
}
