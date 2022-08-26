package com.refreshing.datalayer.models.driver


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
)