package com.refreshing.ui.gallery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.refreshing.R
import com.refreshing.databinding.FragmentTodayBinding
import com.refreshing.databinding.FragmentYesterdayBinding
import com.refreshing.datalayer.models.ordersHistory.OrderHistory
import com.refreshing.ui.gallery.GalleryViewModel
import com.refreshing.ui.gallery.adapter.TodayAdapterRecyclerView
import com.refreshing.ui.gallery.adapter.YesterdayAdapterRecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Yesterday.newInstance] factory method to
 * create an instance of this fragment.
 */
class Yesterday : Fragment() , AdapterView.OnItemSelectedListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentYesterdayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    var orders: OrderHistory?=null
    val spinnerList = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentYesterdayBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val galleryViewModel =
            activity?.let { ViewModelProvider(it).get(GalleryViewModel::class.java) }

        galleryViewModel?.result?.observe(viewLifecycleOwner, Observer {
            orders=it
            Log.e("resulhistory", it.data.toString())

            var totalPriceAccpted = 0.0
            var totalPriceRefused = 0.0
            var totalPriceComplete = 0.0
            var totalPriceShipped = 0.0
            var totalPriceWaiting = 0.0
            it.data?.todayOrder?.wait?.forEach {
                totalPriceWaiting += it?.total?.toDouble() ?: 0.0
            }
            it.data?.yesterdayOrder?.accepted?.forEach {
                totalPriceAccpted += it?.total?.toDouble() ?: 0.0
            }

            it.data?.yesterdayOrder?.refused?.forEach {
                totalPriceRefused += it?.total?.toDouble() ?: 0.0
            }

            it.data?.yesterdayOrder?.complete?.forEach {
                totalPriceComplete += it?.total?.toDouble() ?: 0.0
            }

            it.data?.yesterdayOrder?.shipped?.forEach {
                totalPriceShipped += it?.total?.toDouble() ?: 0.0
            }

            spinnerList.add(getString(R.string.Waiting) + " " + it.data?.todayOrder?.wait?.size.toString() + " - " +  totalPriceWaiting  + " " + getString(
                R.string.KG))

            spinnerList.add(getString(R.string.AcceptedCount) + " " + it.data?.yesterdayOrder?.accepted?.size.toString() + " - " + totalPriceAccpted  + " " + getString(
                R.string.KG))
            spinnerList.add(getString(R.string.countRefused) + " " + it.data?.yesterdayOrder?.refused?.size.toString() + " - " + totalPriceRefused  + " " + getString(
                R.string.KG))
            spinnerList.add(getString(R.string.CompleteCount) + " " + it.data?.yesterdayOrder?.complete?.size.toString() + " - " + totalPriceComplete  + " " + getString(
                R.string.KG))
            spinnerList.add(getString(R.string.CountShiped) + " " + it.data?.yesterdayOrder?.shipped?.size.toString() + " - " + totalPriceShipped  + " " + getString(
                R.string.KG))


            setSpinner(spinnerList)

        })

    }

    fun setSpinner(strings: ArrayList<String>) {

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, strings) }
        // Set layout to use when the list of choices appear
        aa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding.spinner.adapter = aa
        binding.spinner?.onItemSelectedListener = this
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        binding.recyclerViewAcceptedItems.layoutManager = linearLayoutManager
        when (position) {
            0 -> {

                binding.recyclerViewAcceptedItems.adapter = orders?.let { YesterdayAdapterRecyclerView(it,"wait")


                }
            }
            4 -> {
                binding.recyclerViewAcceptedItems.adapter = orders?.let { YesterdayAdapterRecyclerView(it,"shipped")


                }

            }
            1 -> {
                binding.recyclerViewAcceptedItems.adapter = orders?.let {YesterdayAdapterRecyclerView(it,"accepted")


                }

            }
            2 -> {
                binding.recyclerViewAcceptedItems.adapter = orders?.let { YesterdayAdapterRecyclerView(it,"refused")


                }

            }
            3 -> {
                binding.recyclerViewAcceptedItems.adapter = orders?.let { YesterdayAdapterRecyclerView(it,"complete")


                }

            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Yesterday.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Yesterday().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}