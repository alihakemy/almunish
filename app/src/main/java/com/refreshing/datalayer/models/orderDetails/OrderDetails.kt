package com.refreshing.datalayer.models.orderDetails


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class OrderDetails(
    @SerializedName("data")
    var `data`: List<Data>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
): Parcelable