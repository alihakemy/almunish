package com.refreshing.ui.orderdetails.productadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refreshing.databinding.ItemBinding
import com.refreshing.databinding.NewOrderItemBinding
import com.refreshing.databinding.ProductItemsBinding
import com.refreshing.datalayer.convertToMillisecond
import com.refreshing.datalayer.models.New
import com.refreshing.datalayer.models.orderDetails.Product
import com.refreshing.datalayer.timeSince
import com.refreshing.ui.orderdetails.OrderDetails.Companion.startOrderDetails

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.NewViewHolder>() {

    var new: ArrayList<Product> = ArrayList()

   public fun submitNew(newitems: ArrayList<Product>){
       if(!new.isEmpty()){
           new.clear()
       }

        new.addAll(newitems)
        notifyDataSetChanged()
       Log.e("ItemsALI",newitems.size.toString())

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {


      return  NewViewHolder( ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {

        holder.bindData(new[position])
    }

    override fun getItemCount(): Int {

        return new.size
    }

    inner class NewViewHolder(val binding : ItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindData(get: Product) {


            binding.textView.text=get.price.toString()
            binding.textView5.text=get.name.toString()
            binding.feature.text= get.feature.toString()
            if(get.feature.isNullOrEmpty()){
                binding.feature.visibility=View.GONE
            }else{
                binding.feature.visibility=View.VISIBLE
            }

            binding.price.text="x "+get.qyt.toString()


        }


    }


}










