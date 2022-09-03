package com.refreshing.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refreshing.datalayer.apis
import com.refreshing.datalayer.models.ordersHistory.OrderHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val apis: apis) : ViewModel() {


    val result: MutableLiveData<OrderHistory> = MutableLiveData()



    fun getOrderHistory() {
        viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {

                result.postValue(
                    apis.getOrdersHistory())
            }
        }
    }

}