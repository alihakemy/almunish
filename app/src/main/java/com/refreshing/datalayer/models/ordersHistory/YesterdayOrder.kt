package com.refreshing.datalayer.models.ordersHistory


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.refreshing.datalayer.models.New

@Keep
data class YesterdayOrder(
    @SerializedName("accepted")
    var accepted: List<New?>?,
    @SerializedName("complete")
    var complete: List<New?>?,
    @SerializedName("countOrder")
    var countOrder: Int?,
    @SerializedName("countOrdercomplete")
    var countOrdercomplete: Int?,
    @SerializedName("refused")
    var refused: List<New?>?,
    @SerializedName("shipped")
    var shipped: List<New?>?,
    @SerializedName("totalCompleteOrder")
    var totalCompleteOrder: String?,
    @SerializedName("wait")
    var wait: List<New?>?
)