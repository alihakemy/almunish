package com.refreshing.datalayer.models


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("accepted")
    var accepted: ArrayList<Accepted>?,
    @SerializedName("upcoming")
    var upComing: List<UpComming>?,
    @SerializedName("new")
    var new: ArrayList<New>?
)