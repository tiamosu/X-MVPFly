package com.xia.fly.module.common.router

import com.alibaba.android.arouter.launcher.ARouter
import com.xia.fly.module.common.ui.EmptyFragment
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author weixia
 * @date 2019/3/15.
 */
object Router {

    fun obtainFragmentA(): ISupportFragment {
        return obtainFragment(RouterContant.FRAGMENT_A)
    }

    fun obtainFragmentB(): ISupportFragment {
        return obtainFragment(RouterContant.FRAGMENT_B)
    }

    private fun obtainFragment(routerPath: String): ISupportFragment {
        val fragment = ARouter.getInstance().build(routerPath).navigation() as? ISupportFragment?
        return fragment ?: EmptyFragment()
    }
}
