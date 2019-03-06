package com.xia.fly.utils

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import java.util.regex.Pattern
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * 处理字符串的工具类
 *
 * @author xia
 * @date 2018/9/14.
 */
@Suppress("unused")
class CharacterHandler private constructor() {

    init {
        throw IllegalStateException("u can't instantiate me!")
    }

    companion object {

        /**
         * emoji过滤器
         */
        @JvmStatic
        val EMOJI_FILTER: InputFilter = object : InputFilter {
            val emoji = Pattern.compile(
                    "[\ud83c][\udc00-\udfff]|[\ud83d][\udc00-\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE)

            override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                val emojiMatcher = emoji.matcher(source)
                return if (emojiMatcher.find()) {
                    ""
                } else null
            }
        }

        /**
         * 字符串转换成十六进制字符串
         *
         * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
         */
        @JvmStatic
        fun str2HexStr(str: String): String {
            val chars = "0123456789ABCDEF".toCharArray()
            val sb = StringBuilder()
            val bs = str.toByteArray()
            var bit: Int

            for (b in bs) {
                bit = b.toInt() and 0x0f0 shr 4
                sb.append(chars[bit])
                bit = b.toInt() and 0x0f
                sb.append(chars[bit])
            }
            return sb.toString().trim { it <= ' ' }
        }

        /**
         * json 格式化
         *
         * @param json
         * @return
         */
        @JvmStatic
        fun jsonFormat(json: String): String {
            var str = json
            if (TextUtils.isEmpty(str)) {
                return "Empty/Null json content"
            }
            var message: String
            try {
                str = str.trim { it <= ' ' }
                message = when {
                    str.startsWith("{") -> {
                        val jsonObject = JSONObject(str)
                        jsonObject.toString(4)
                    }
                    str.startsWith("[") -> {
                        val jsonArray = JSONArray(str)
                        jsonArray.toString(4)
                    }
                    else -> str
                }
            } catch (e: JSONException) {
                message = str
            }
            return message
        }

        /**
         * xml 格式化
         *
         * @param xml
         * @return
         */
        @JvmStatic
        fun xmlFormat(xml: String): String {
            if (TextUtils.isEmpty(xml)) {
                return "Empty/Null xml content"
            }
            val message: String
            message = try {
                val xmlInput = StreamSource(StringReader(xml))
                val xmlOutput = StreamResult(StringWriter())
                val transformer = TransformerFactory.newInstance().newTransformer()
                transformer.setOutputProperty(OutputKeys.INDENT, "yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
                transformer.transform(xmlInput, xmlOutput)
                xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
            } catch (e: TransformerException) {
                xml
            }
            return message
        }
    }
}
