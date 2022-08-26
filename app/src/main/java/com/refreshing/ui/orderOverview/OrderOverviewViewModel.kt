package com.refreshing.ui.orderOverview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refreshing.datalayer.apis
import com.refreshing.datalayer.models.Orders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderOverviewViewModel @Inject constructor(private val apis: apis) : ViewModel() {



    val orders :MutableLiveData<Orders> = MutableLiveData()

    val OrderErrors :MutableLiveData<String> = MutableLiveData()

    fun getOrders(){
        viewModelScope.launch {
            kotlin.runCatching {
                apis.getOrders()
            }.onSuccess {

                orders.postValue(it)
            }.onFailure {

                OrderErrors.postValue(it.localizedMessage.toString())
            }
        }

    }


    fun connectOrder(connectOrder:String){
        val map =HashMap<String,String>()
        map.put("connectOrder",connectOrder)


        viewModelScope.launch {

            kotlin.runCatching {

                apis.connectOrder(map)

            }

        }


    }


}