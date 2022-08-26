package com.refreshing.datalayer.models.orderDetails


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Data(
    @SerializedName("drive")
    var drive: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("order_created")
    var orderCreated: String?,
    @SerializedName("order_number")
    var orderNumber: String?,
    @SerializedName("payment_method")
    var paymentMethod: String?,
    @SerializedName("productCount")
    var productCount: Int?,
    @SerializedName("products")
    var products: ArrayList<Product>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("total")
    var total: String?,
    @SerializedName("user")
    var user: String?,
    @SerializedName("price_ship")
    var price_ship: String?,
    @SerializedName("discount")
    var discount: String? ,
    @SerializedName("address")
    var address:String?,
    @SerializedName("phone")
    var phone: String?,

    @SerializedName("lat")
    var lat:String? ="48",
    @SerializedName("long")
    var long: String? ="19",
    @SerializedName("total_without_ship")
    var total_without_ship: String? ,
): Parcelable