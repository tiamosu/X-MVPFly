package com.xia.fly.base.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * [FragmentDelegate] 默认实现类
 *
 * @author xia
 * @date 2018/9/20.
 */
@Suppress("UNUSED_PARAMETER")
class FragmentDelegateImpl(mFragmentManager: FragmentManager, mFragment: Fragment) : FragmentDelegate {

    override fun onAttach(context: Context) {}

    override fun onCreate(savedInstanceState: Bundle?) {}

    override fun onCreateView(view: View?, savedInstanceState: Bundle?) {}

    override fun onActivityCreate(savedInstanceState: Bundle?) {}

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onSaveInstanceState(outState: Bundle) {}

    override fun onDestroyView() {}

    override fun onDestroy() {}

    override fun onDetach() {}

    override fun isAdded(): Boolean {
        return false
    }
}
