package com.xia.fly.module.common.router

import com.alibaba.android.arouter.facade.template.IProvider
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author weixia
 * @date 2019/5/8.
 */
interface IProviderA : IProvider {

    fun obtainACls(): Class<out ISupportFragment>
}
