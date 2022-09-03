package com.refreshing.datalayer

import com.refreshing.datalayer.models.Orders
import com.refreshing.datalayer.models.driver.DriverModel
import com.refreshing.datalayer.models.ordersHistory.OrderHistory
import com.refreshing.ui.orderdetails.OrderDetails
import retrofit2.http.*


interface apis {


    @GET("api/orders")
    suspend fun getOrders():Orders


    @GET("api/orderDetails/{order}")
    suspend fun orderDetail(
        @Path("order") productId: String,

        ): com.refreshing.datalayer.models.orderDetails.OrderDetails


    @POST("api/changeStatus")
    suspend fun updateStatus(
        @Body hashMap: HashMap<String, String>

        )

    @POST("api/connectOrder")
    suspend fun connectOrder(
        @Body hashMap: HashMap<String, String>

    )


    @POST("api/login")
    suspend fun login(
        @Body hashMap: HashMap<String, String>

    )
    @GET("api/drivers")
    suspend fun getDrivers():DriverModel

    @POST("api/checkDriver")
    suspend fun checkDriver(
        @Body hashMap: HashMap<String, String>

    )

    @GET("api/ordersHistory")
    suspend fun  getOrdersHistory():OrderHistory
}