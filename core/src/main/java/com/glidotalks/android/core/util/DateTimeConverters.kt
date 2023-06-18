package com.glidotalks.android.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeConverters {

    fun getMonthYear(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }

}