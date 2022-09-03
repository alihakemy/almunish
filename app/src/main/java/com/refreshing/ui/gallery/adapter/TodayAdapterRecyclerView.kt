package com.refreshing.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refreshing.databinding.ItemListBinding
import com.refreshing.datalayer.convertToMillisecond
import com.refreshing.datalayer.models.New
import com.refreshing.datalayer.models.ordersHistory.OrderHistory
import com.refreshing.datalayer.timeSince

class TodayAdapterRecyclerView(val orderHistory: OrderHistory, val type: String) :
    RecyclerView.Adapter<TodayAdapterRecyclerView.mViewHolder>() {

    inner class mViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderHistory: New?) {

            binding.textView28.text=
                timeSince(convertToMillisecond(orderHistory?.orderCreated+":00"),binding.root.context)

            binding.textView31.text="#"+orderHistory?.orderNumber.toString()
            binding.textView33.text=orderHistory?.total.toString()


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

            holder.bind(orderHistory.data?.todayOrder?.wait?.get(position))
        } else if (type.equals("shipped")) {
            holder.bind(orderHistory.data?.todayOrder?.shipped?.get(position))

        } else if (type.equals("accepted")) {
            holder.bind(orderHistory.data?.todayOrder?.accepted?.get(position))

        } else if (type.equals("refused")) {
            holder.bind(orderHistory.data?.todayOrder?.refused?.get(position))

        } else if (type.equals("complete")) {
            holder.bind(orderHistory.data?.todayOrder?.complete?.get(position))

        }

    }

    override fun getItemCount(): Int {

        if (type.equals("wait")) {
            return orderHistory.data?.todayOrder?.wait?.size ?: 0
        } else if (type.equals("shipped")) {
            return orderHistory.data?.todayOrder?.shipped?.size ?: 0

        } else if (type.equals("accepted")) {
            return orderHistory.data?.todayOrder?.accepted?.size ?: 0

        } else if (type.equals("refused")) {
            return orderHistory.data?.todayOrder?.refused?.size ?: 0

        } else if (type.equals("complete")) {
            return orderHistory.data?.todayOrder?.complete?.size ?: 0

        } else {
            return 0
        }
    }


}