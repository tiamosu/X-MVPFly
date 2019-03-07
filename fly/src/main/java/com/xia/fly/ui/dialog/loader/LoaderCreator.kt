package com.xia.fly.ui.dialog.loader

import com.wang.avi.AVLoadingIndicatorView
import com.wang.avi.Indicator
import java.util.*

/**
 * @author xia
 * @date 2018/7/29.
 */
object LoaderCreator {
    private val LOADING_MAP = WeakHashMap<String, Indicator>()

    fun create(type: String, avLoadingIndicatorView: AVLoadingIndicatorView): AVLoadingIndicatorView {
        val indicator = LOADING_MAP[type] ?: getIndicator(type)
        avLoadingIndicatorView.indicator = indicator
        return avLoadingIndicatorView
    }

    private fun getIndicator(name: String?): Indicator? {
        if (name == null || name.isEmpty()) {
            return null
        }
        val drawableClassName = StringBuilder()
        if (!name.contains(".")) {
            val defaultPackageName = AVLoadingIndicatorView::class.java.getPackage()!!.name
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".")
        }
        drawableClassName.append(name)
        return try {
            val drawableClass = Class.forName(drawableClassName.toString())
            drawableClass.newInstance() as Indicator
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
