package com.refreshing.datalayer.models.driver


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DriverModel(
    @SerializedName("data")
    var `data`: ArrayList<Data>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)