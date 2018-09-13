package me.yangcx.funnyimage.utils

import org.joda.time.DateTime

object TimeStampUtils {
    fun getNowString(pattern: String) = DateTime.now().toString(pattern)
}