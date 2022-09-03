package com.refreshing.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refreshing.databinding.ItemListBinding
import com.refreshing.datalayer.convertToMillisecond
import com.refreshing.datalayer.models.New
import com.refreshing.datalayer.models.ordersHistory.OrderHistory
import com.refreshing.datalayer.timeSince
import com.refreshing.ui.orderdetails.OrderDetails

class YesterdayAdapterRecyclerView(val orderHistory: OrderHistory, val type: String) :
    RecyclerView.Adapter<YesterdayAdapterRecyclerView.mViewHolder>() {

    inner class mViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderHistory: New?) {

            binding.textView28.text=
                timeSince(convertToMillisecond(orderHistory?.orderCreated+":00"),binding.root.context)

            binding.textView31.text="#"+orderHistory?.orderNumber.toString()
            binding.textView33.text=orderHistory?.total.toString()

            binding.root.setOnClickListener {
                OrderDetails.startOrderDetails(it.context, orderHistory?.id.toString())

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        return mViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {

        if (type.equals("wait")) {

            holder.bind(orderHistory.data?.yesterdayOrder?.wait?.get(position))
        } else if (type.equals("shipped")) {
            holder.bind(orderHistory.data?.yesterdayOrder?.shipped?.get(position))

        } else if (type.equals("accepted")) {
            holder.bind(orderHistory.data?.yesterdayOrder?.accepted?.get(position))

        } else if (type.equals("refused")) {
            holder.bind(orderHistory.data?.yesterdayOrder?.refused?.get(position))

        } else if (type.equals("complete")) {
            holder.bind(orderHistory.data?.yesterdayOrder?.complete?.get(position))

        }

    }

    override fun getItemCount(): Int {

        if (type.equals("wait")) {
            return orderHistory.data?.yesterdayOrder?.wait?.size ?: 0
        } else if (type.equals("shipped")) {
            return orderHistory.data?.yesterdayOrder?.shipped?.size ?: 0

        } else if (type.equals("accepted")) {
            return orderHistory.data?.yesterdayOrder?.accepted?.size ?: 0

        } else if (type.equals("refused")) {
            return orderHistory.data?.yesterdayOrder?.refused?.size ?: 0

        } else if (type.equals("complete")) {
            return orderHistory.data?.yesterdayOrder?.complete?.size ?: 0

        } else {
            return 0
        }
    }


}