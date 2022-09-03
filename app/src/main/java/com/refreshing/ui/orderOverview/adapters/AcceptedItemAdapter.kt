package com.refreshing.ui.orderOverview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refreshing.databinding.AcceptedItemBinding
import com.refreshing.datalayer.convertToMillisecond
import com.refreshing.datalayer.models.Accepted
import com.refreshing.datalayer.timeSince
import com.refreshing.ui.orderdetails.OrderDetails

class AcceptedItemAdapter : RecyclerView.Adapter<AcceptedItemAdapter.NewViewHolder>() {

    var new: ArrayList<Accepted> = ArrayList()

   public fun submitNew(newitems: ArrayList<Accepted>){
       if(!new.isEmpty()){
           new.clear()
       }

        new.addAll(newitems)
        notifyDataSetChanged()
       Log.e("ItemsALI",newitems.size.toString())

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {


      return  NewViewHolder( AcceptedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {

        holder.bindData(new[position])
    }

    override fun getItemCount(): Int {

        return new.size
    }

    inner class NewViewHolder(val binding :AcceptedItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindData(get: Accepted) {
            binding.textView5.text="No. #"+get?.orderNumber
            binding.feature.text=get.productCount.toString() +" Item"
            binding.feature2.text= timeSince(convertToMillisecond(get.orderCreated.toString()+":00"),binding.root.context)
            binding.root.setOnClickListener {
                OrderDetails.startOrderDetails(it.context, get.id.toString())
            }

        }


    }


}










