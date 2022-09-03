package com.refreshing.ui.orderdetails

import android.R
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.refreshing.databinding.ActivityOrderDetailsBinding
import com.refreshing.datalayer.convertToMillisecond
import com.refreshing.datalayer.models.driver.Data
import com.refreshing.datalayer.timeSince
import com.refreshing.ui.Print.Companion.startPrint
import com.refreshing.ui.base.BaseActivity
import com.refreshing.ui.orderdetails.productadapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderDetails : BaseActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityOrderDetailsBinding
    var adapter : ProductAdapter? =null
    lateinit var pd: ProgressDialog

    var item :com.refreshing.datalayer.models.orderDetails.OrderDetails?=null
  var selectedDriverID:String =""
    var selectedDriverName:String =""

    val driverModel:ArrayList<Data> = ArrayList()

    lateinit var viewModel:OrderDetailsViewModel
    var orderId:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=  ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
     viewModel =
            ViewModelProvider(this)[OrderDetailsViewModel::class.java]

        val id =intent.extras?.getString("orderId")
        pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.show()

        pd.setCancelable(false)
        orderId=id.toString()
        Log.e("IddALI", id.toString())
        viewModel.getDetails(id.toString())
        binding?.recyclerViewNewItems?.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        adapter= ProductAdapter()
        binding?.recyclerViewNewItems?.adapter=adapter
        viewModel.orders.observe(this, Observer {

            if(it.data?.isNullOrEmpty() == true){
                return@Observer
            }
            item=it

            if (pd.isShowing) {
                pd.dismiss()
            }
            binding.imageView2.visibility=View.GONE
            binding.textView7.visibility=View.VISIBLE
            binding.textView.text=" Almuneish "+it.data?.first()?.user.toString()

            binding.orderNumber.text=" # "+it.data?.first()?.orderNumber.toString()
            binding.userNotes.text=item?.data?.first()?.note.toString()


            binding.textView6.text=" # "+ it.data?.first()?.orderNumber.toString()

            binding.paymentMethod.text=it.data?.first()?.paymentMethod.toString()
            binding.status.text=it.data?.first()?.status.toString()

            binding. timeFrom.text=
                timeSince(convertToMillisecond(it.data?.first()?.orderCreated.toString()+":00"),binding.root.context)


            it.data?.first()?.products?.let { it1 -> adapter?.submitNew(it1) }

//            binding.textView28.text=it.data?.first()?.drive.toString()


            binding.shippingPrice.text=it.data?.first()?.price_ship.toString()

            binding.status2.text=it.data?.first()?.address.toString()

            val shipprice =it.data?.first()?.price_ship?.toDouble() ?:0.0
            val total  =it.data?.first()?.total?.toDouble() ?:0.0

            binding.disCount2.text = (item?.data?.first()?.discount?.toDouble() ?: 0.0).toString()
            binding.total.text = (total).toString()

            if((!it.data?.first()?.status.toString().equals("wait") )){
                binding.imageView2.visibility=View.VISIBLE
                binding.textView7.visibility=View.GONE
                binding.button.isClickable=false
                binding.button.text="Order Accepted"

                binding.button.setBackgroundColor(Color.parseColor("#32CD32"))
            }

            if((it.data?.first()?.status.toString().equals("refused") )){
                binding.imageView2.visibility=View.GONE
                binding.textView7.visibility=View.GONE
                binding.button.text="Order refused"
                binding.button.setBackgroundColor(Color.parseColor("#ff0000"))
            }
            if((it.data?.first()?.status.toString().equals("wait") )){
//                binding.textView28.visibility=View.GONE

            }


        })

        viewModel.OrderErrors.observe(this, Observer {
            Log.e("IddALI", it.toString())
            if (pd.isShowing) {
                pd.dismiss()
            }
            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
        })

        binding.imageView3.setOnClickListener {
            onBackPressed()
        }
        binding.button.setOnClickListener {
            binding.imageView2.visibility=View.VISIBLE
            binding.textView7.visibility=View.GONE

            viewModel.accept("accepted",id.toString())
            binding.button.isClickable=false
            binding.button.text="Ready To Delivery"
            binding.button.setBackgroundColor(Color.parseColor("#32CD32"))

            binding.status.text="Ready To Delivery"
            item?.let { it1 ->
                it1.data?.forEach {
                    it.drive=selectedDriverName

                }
                startPrint(it1,this) }


        }

        //declind
        binding.textView7.setOnClickListener {


            val builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle("Confirm")
            builder.setMessage("Are you sure?")

            builder.setPositiveButton("YES",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                    viewModel.accept("refused",id.toString())
                    binding.imageView2.visibility=View.GONE
                    binding.textView7.visibility=View.GONE
                    binding.button.isClickable=false
                    binding.button.text="Order refused"
                    binding.button.setBackgroundColor(Color.parseColor("#ff0000"))
                    dialog.dismiss()
                })

            builder.setNegativeButton("NO",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing
                    dialog.dismiss()
                })

            val alert: AlertDialog = builder.create()
            alert.show()



        }

        //print
        binding.imageView2.setOnClickListener {

            item?.let { it1 ->
                it1.data?.forEach {
                    it.drive=selectedDriverName

                }
                startPrint(it1,this) }

        }

        val driverName:ArrayList<String> = ArrayList()

        viewModel.getDriver()
        viewModel.driver.observe(this, Observer {
            if(!it.data.isNullOrEmpty()){

            driverModel.clear()
            driverName.clear()
            driverModel.addAll(it.data?: emptyList())
            selectedDriverID= it.data?.first()?.id.toString()
            selectedDriverName= it.data?.first()?.name.toString()
            viewModel.addDriver(selectedDriverID,orderId)
            it.data?.forEach {

                driverName.add(it.name.toString())
                Log.e("DriverNames",driverName.toString())
            }
            binding.driverName.onItemSelectedListener = this


            // Creating adapter for spinner
            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(this, R.layout.simple_spinner_item, driverName)


            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)


            // attaching data adapter to spinner
            binding.driverName.setAdapter(dataAdapter)
            }
        })


    }
  override  fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // On selecting a spinner item
        val item = parent.getItemAtPosition(position).toString()
         selectedDriverID= driverModel.get(position).id.toString()
        // Showing selected spinner item
      viewModel.addDriver(selectedDriverID,orderId)
     selectedDriverName = driverModel.get(position).name.toString()
      Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
        // TODO Auto-generated method stub
    }
    companion object{
        fun startOrderDetails (context: Context,orderId:String) {
            val intent =Intent(context,OrderDetails::class.java)
            intent.putExtra("orderId",orderId)
            context.startActivity(intent)
        }
    }


}