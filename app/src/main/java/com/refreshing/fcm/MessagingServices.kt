package com.refreshing.fcm

import android.content.Intent
import android.util.Log
import com.refreshing.fcm.broadCast.LocalBroadCast.sendBroadCastPostUploaded
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.refreshing.fcm.channelManager.ChannelId
import com.refreshing.datalayer.models.New
import com.refreshing.ui.orderdetails.OrderDetails

class MessagingServices : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val data = remoteMessage.data
        val notification = remoteMessage.notification
        Log.e("DataFCM", data.toString())
        Log.e("DataFCM", notification?.body.toString())

        //{price_ship=1.000, payment_method=الدفع عند الاستلام,
        // status=wait, id=116, body= طلب جديد, title=لديك طلب جديد برقم 99973,
        // total=2.5, order_number=99973, productCount=1, created=20-07-2022 22:22}

        var price_ship = data["price_ship"]
        var payment_method = data["payment_method"]
        var status = data["status"]
        var id = data["id"]
        var order_number = data["order_number"]
        var productCount = data["productCount"]
        var order_created = data["created"]
        var total = data["total"]
        var dicount = data["discount"]
        var address = data["address"]

        val item = New(
            id = id?.toInt(),
            status = status,
            price_ship = price_ship?.toDouble(),
            paymentMethod = payment_method,
            orderNumber = order_number,
            productCount = productCount?.toInt(),
            orderCreated = order_created,
            total = total?.toDouble()?.toString(),
            discount = dicount?.toDouble() ?: 0.0, address = address
        )
        val intent = Intent(this, OrderDetails::class.java)

        intent.putExtra("orderId", id.toString())
        NotificationManager.showBasicNotificationChats(
            this,
            ChannelId.ViewProduct.name,
            NotificationAtomicId.getAtomicId(),
            intent,
            " طلب رقم  " + data["order_number"],
            data["total"] + " الكميه "
        )

        sendBroadCastPostUploaded(this, item)
    }


}