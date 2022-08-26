package com.refreshing.datalayer

import android.graphics.Color
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import java.text.SimpleDateFormat

fun String.setFontSizeForPath(path: String, fontSizeInPixel: Int, colorCode: String = "#FF000000"): SpannableString {
    val spannable = SpannableString(this)
    val startIndexOfPath = spannable.toString().indexOf(path)
    spannable.setSpan(
        AbsoluteSizeSpan(fontSizeInPixel),
        startIndexOfPath,
        startIndexOfPath + path.length,
        0
    )
    spannable.setSpan(
        ForegroundColorSpan(Color.parseColor(colorCode)),
        startIndexOfPath,
        startIndexOfPath + path.length,
        0
    )

    return spannable
}
fun convertToMillisecond(date: String): Long {


    return SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").parse(date.replace(" ", "T"))?.time
        ?: System.currentTimeMillis()

}

fun timeSince(date: Long): String {

    var seconds = 0.0
    seconds = if (date > System.currentTimeMillis()) {
        Math.floor(((date-System.currentTimeMillis()) / 1000).toDouble())

    } else {
        Math.floor(((System.currentTimeMillis() - date) / 1000).toDouble())

    }

    var interval = seconds / 31536000;

    if (interval > 1) {
        return Math.floor(interval).toInt().toString() + " سنه "
    }
    interval = seconds / 2592000;
    if (interval > 1) {
        return Math.floor(interval).toInt().toString() + " شهر "
    }
    interval = seconds / 86400;
    if (interval > 1) {
        return Math.floor(interval).toInt().toString() + " يوم "
    }
    interval = seconds / 3600;
    if (interval > 1) {
        return Math.floor(interval).toInt().toString() + " ساعه"
    }
    interval = seconds / 60;
    if (interval > 1) {
        return Math.floor(interval).toInt().toString() + " دقيقه"
    }
    //المفروض الاندرويد والايفون يرفعوا على timeZone ثابته بالنسبه ل الاتنين

    return if (Math.floor(seconds).toInt() <= 0) {
        "منذو دقائق"

    } else {
        Math.floor(seconds).toInt().toString() + "ثوانى"

    }
}