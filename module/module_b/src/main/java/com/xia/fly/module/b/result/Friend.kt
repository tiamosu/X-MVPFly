package com.xia.fly.module.b.result

import java.util.ArrayList

/**
 * @author weixia
 * @date 2019/6/11.
 */
class Friend {
    private val data: List<FriendBean>? = null

    inner class FriendBean {
        val id: Int = 0
        val link: String? = null
        val name: String? = null
        val order: Int = 0
        val visible: Int = 0

        override fun toString(): String {
            return "FriendBean{" +
                    "id=" + id +
                    ", link='" + link + '\''.toString() +
                    ", name='" + name + '\''.toString() +
                    ", order=" + order +
                    ", visible=" + visible +
                    '}'.toString()
        }
    }

    fun getData(): List<FriendBean> {
        return data ?: ArrayList()
    }
}
