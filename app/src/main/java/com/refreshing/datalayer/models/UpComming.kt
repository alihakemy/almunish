package com.refreshing.datalayer.models


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UpComming(
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
    @SerializedName("status")
    var status: String?,
    @SerializedName("total")
    var total: Double?,
    @SerializedName("price_ship")
    var price_ship: Double?,
    @SerializedName("discount")
    var discount: Double?
    ,
    @SerializedName("address")
    var address:String?
)