package com.xia.fly.module.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xia.fly.module.common.R
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author weixia
 * @date 2019/3/15.
 */
class EmptyFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }
}
