package com.refreshing.datalayer.models.ordersHistory


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OrderHistory(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)