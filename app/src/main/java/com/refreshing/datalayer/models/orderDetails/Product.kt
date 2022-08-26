package com.refreshing.datalayer.models.orderDetails


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Product(
    @SerializedName("feature")
    var feature: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("price")
    var price: String?,
    @SerializedName("qyt")
    var qyt: Int?
): Parcelable