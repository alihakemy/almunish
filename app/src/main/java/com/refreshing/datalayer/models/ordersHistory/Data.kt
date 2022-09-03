package com.refreshing.datalayer.models.ordersHistory


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("todayOrder")
    var todayOrder: TodayOrder?,
    @SerializedName("yesterdayOrder")
    var yesterdayOrder: YesterdayOrder?
)