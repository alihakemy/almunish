package com.refreshing.ui.orderdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refreshing.datalayer.apis
import com.refreshing.datalayer.models.Orders
import com.refreshing.datalayer.models.driver.DriverModel
import com.refreshing.datalayer.models.orderDetails.OrderDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(private val apis: apis) : ViewModel() {

    val orders: MutableLiveData<OrderDetails> = MutableLiveData()

    val OrderErrors: MutableLiveData<String> = MutableLiveData()

    fun getDetails(orderId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                apis.orderDetail(orderId)
            }.onSuccess {

                orders.postValue(it)
            }.onFailure {

                OrderErrors.postValue(it.localizedMessage.toString())

            }
        }

    }


    fun accept(changeStatus: String, id: String) {
        val map = HashMap<String, String>()
        map.put("changeStatus", changeStatus)
        map.put("id", id)

        viewModelScope.launch {

            kotlin.runCatching {

                apis.updateStatus(map)

            }

        }


    }

    val driver: MutableLiveData<DriverModel> = MutableLiveData()

    fun getDriver() {
        viewModelScope.launch {
            kotlin.runCatching {
                driver.postValue(
                    apis.getDrivers())
            }

        }
    }


    fun addDriver(driverID: String, OrderId: String) {
        val map = HashMap<String, String>()
        map.put("id", driverID)
        map.put("idorder", OrderId)

        viewModelScope.launch {

            kotlin.runCatching {

                apis.checkDriver(map)

            }

        }


    }

}