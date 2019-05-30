package com.xia.fly.base.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * [Fragment] 代理类,用于框架内部在每个 [Fragment] 的对应生命周期中插入需要的逻辑
 * @see [FragmentDelegateImpl]
 * @see [FragmentDelegate wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki#3.13)
 *
 * @author xia
 * @date 2018/9/20.
 */
interface FragmentDelegate {

    /**
     * Return true if the fragment is currently added to its activity.
     */
    fun isAdded(): Boolean

    fun onAttach(context: Context)

    fun onCreate(savedInstanceState: Bundle?)

    fun onCreateView(view: View?, savedInstanceState: Bundle?)

    fun onActivityCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroyView()

    fun onDestroy()

    fun onDetach()

    companion object {

        const val FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE"
    }
}
