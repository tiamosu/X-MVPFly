package com.xia.fly.utils

/**
 * @author xia
 * @date 2018/9/14.
 */
@Suppress("unused")
object Preconditions {

    @JvmStatic
    fun checkArgument(expression: Boolean) {
        if (!expression) {
            throw IllegalArgumentException()
        }
    }

    @JvmStatic
    fun checkArgument(expression: Boolean, errorMessage: Any?) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage.toString())
        }
    }

    @JvmStatic
    fun checkArgument(expression: Boolean, errorMessageTemplate: String?, vararg errorMessageArgs: Any) {
        if (!expression) {
            throw IllegalArgumentException(format(errorMessageTemplate, *errorMessageArgs))
        }
    }

    @JvmStatic
    fun checkState(expression: Boolean) {
        if (!expression) {
            throw IllegalStateException()
        }
    }

    @JvmStatic
    fun checkState(expression: Boolean, errorMessage: Any?) {
        if (!expression) {
            throw IllegalStateException(errorMessage.toString())
        }
    }

    @JvmStatic
    fun checkState(expression: Boolean, errorMessageTemplate: String?, vararg errorMessageArgs: Any) {
        if (!expression) {
            throw IllegalStateException(format(errorMessageTemplate, *errorMessageArgs))
        }
    }

    @JvmStatic
    fun <T> checkNotNull(reference: T?): T {
        return if (reference == null) {
            throw NullPointerException()
        } else {
            reference
        }
    }

    @JvmStatic
    fun <T> checkNotNull(reference: T?, errorMessage: Any?): T {
        return reference ?: throw NullPointerException(errorMessage.toString())
    }

    @JvmStatic
    fun <T> checkNotNull(reference: T?, errorMessageTemplate: String?, vararg errorMessageArgs: Any): T {
        return reference
                ?: throw NullPointerException(format(errorMessageTemplate, *errorMessageArgs))
    }

    @JvmStatic
    @JvmOverloads
    fun checkElementIndex(index: Int, size: Int, desc: String? = "index"): Int {
        return if (index in 0..(size - 1)) {
            index
        } else {
            throw IndexOutOfBoundsException(badElementIndex(index, size, desc))
        }
    }

    @JvmStatic
    private fun badElementIndex(index: Int, size: Int, desc: String?): String? {
        return when {
            index < 0 -> format("%s (%s) must not be negative", desc!!, index)
            size < 0 -> throw IllegalArgumentException("negative size: $size")
            else -> format("%s (%s) must be less than size (%s)", desc!!, index, size)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun checkPositionIndex(index: Int, size: Int, desc: String? = "index"): Int {
        return if (index in 0..size) {
            index
        } else {
            throw IndexOutOfBoundsException(badPositionIndex(index, size, desc))
        }
    }

    @JvmStatic
    private fun badPositionIndex(index: Int, size: Int, desc: String?): String? {
        return when {
            index < 0 -> format("%s (%s) must not be negative", desc!!, index)
            size < 0 -> throw IllegalArgumentException("negative size: $size")
            else -> format("%s (%s) must not be greater than size (%s)", desc!!, index, size)
        }
    }

    @JvmStatic
    fun checkPositionIndexes(start: Int, end: Int, size: Int) {
        if (start < 0 || end < start || end > size) {
            throw IndexOutOfBoundsException(badPositionIndexes(start, end, size))
        }
    }

    @JvmStatic
    private fun badPositionIndexes(start: Int, end: Int, size: Int): String? {
        return if (start in 0..size)
            if (end in 0..size)
                format("end index (%s) must not be less than start index (%s)", end, start)
            else
                badPositionIndex(end, size, "end index")
        else
            badPositionIndex(start, size, "start index")
    }

    @JvmStatic
    private fun format(template: String?, vararg args: Any): String? {
        var str = template
        if (args.isEmpty()) {
            return str
        }
        str = str.toString()
        val builder = StringBuilder(str.length + 16 * args.size)
        var templateStart = 0
        var i = 0
        var placeholderStart: Int
        while (i < args.size) {
            placeholderStart = str.indexOf("%s", templateStart)
            if (placeholderStart == -1) {
                break
            }

            builder.append(str.substring(templateStart, placeholderStart))
            builder.append(args[i++])
            templateStart = placeholderStart + 2
        }

        builder.append(str.substring(templateStart))
        if (i < args.size) {
            builder.append(" [")
            builder.append(args[i++])

            while (i < args.size) {
                builder.append(", ")
                builder.append(args[i++])
            }
            builder.append(']')
        }
        return builder.toString()
    }
}
