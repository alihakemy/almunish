package com.refreshing.ui.orderOverview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refreshing.databinding.NewOrderItemBinding
import com.refreshing.datalayer.convertToMillisecond
import com.refreshing.datalayer.models.New
import com.refreshing.datalayer.timeSince
import com.refreshing.ui.orderdetails.OrderDetails.Companion.startOrderDetails

class NewItemAdapter : RecyclerView.Adapter<NewItemAdapter.NewViewHolder>() {

    var new: ArrayList<New> = ArrayList()

   public fun submitNew(newitems: ArrayList<New>){
       if(!new.isEmpty()){
           new.clear()
       }

        new.addAll(newitems)
        notifyDataSetChanged()
       Log.e("ItemsALI",newitems.size.toString())

    }

    public fun addItem(newitems: New){

        new.add(0,newitems)
    notifyItemInserted(0)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {


      return  NewViewHolder(  NewOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {

        holder.bindData(new[position])
    }

    override fun getItemCount(): Int {

        return new.size
    }

    inner class NewViewHolder(val binding :NewOrderItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindData(get: New) {
            binding.orderName.text="# No."+get.orderNumber.toString()
            binding.itemCount.text=get.productCount.toString() +" Item"

            binding.time.text= timeSince(convertToMillisecond(get.orderCreated.toString()+":00"),binding.root.context)


            binding.root.setOnClickListener {
                startOrderDetails(it.context,get.id.toString())
            }

        }


    }


}










