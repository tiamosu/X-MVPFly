package com.xia.fly.module.a.router

import android.content.Context

import com.alibaba.android.arouter.facade.annotation.Route
import com.xia.fly.module.a.ui.AFragment
import com.xia.fly.module.common.router.IProviderA
import com.xia.fly.module.common.router.RouterConstant
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author weixia
 * @date 2019/5/8.
 */
@Route(path = RouterConstant.PROVIDER_A)
class IProviderAImpl : IProviderA {

    override fun obtainACls(): Class<out ISupportFragment> {
        return AFragment::class.java
    }

    override fun init(context: Context) {}
}
