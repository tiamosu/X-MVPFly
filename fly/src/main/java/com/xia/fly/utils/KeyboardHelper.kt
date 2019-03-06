package com.xia.fly.utils

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.xia.fly.ui.activities.SupportActivity

/**
 * @author xia
 * @date 2018/9/11.
 */
class KeyboardHelper private constructor() {

    init {
        throw IllegalStateException("u can't instantiate me!")
    }

    companion object {

        /**
         * 处理控制 点击屏幕空白区域隐藏软键盘
         */
        @JvmStatic
        fun dispatchTouchEvent(activity: SupportActivity<*>, ev: MotionEvent) {
            if (activity.isDispatchTouchHideKeyboard && ev.action == MotionEvent.ACTION_DOWN) {
                val v = activity.currentFocus
                if (isShouldHideKeyboard(activity, v, ev)) {
                    val iBinder = v!!.windowToken
                    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (iBinder != null) {
                        imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS)
                    }
                }
            }
        }

        private fun isShouldHideKeyboard(activity: SupportActivity<*>, v: View?, event: MotionEvent): Boolean {
            if (v is EditText) {
                val editText = v as EditText?
                activity.onDispatchTouchHideKeyboard(editText!!)
                val l = intArrayOf(0, 0)
                v.getLocationInWindow(l)
                val left = l[0]
                val top = l[1]
                val bottom = top + v.height
                val right = left + v.width
                return !(event.x > left && event.x < right
                        && event.y > top && event.y < bottom)
            }
            return false
        }
    }
}
