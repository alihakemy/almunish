package com.refreshing.ui.orderOverview

import android.app.ProgressDialog
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.refreshing.fcm.broadCast.UPLOAD_COMPLETE_ACTION
import com.refreshing.fcm.broadCast.UPLOAD_POST_EXTRA_NAME
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView
import com.google.firebase.messaging.FirebaseMessaging
import com.refreshing.databinding.FragmentHomeBinding
import com.refreshing.datalayer.models.New
import com.refreshing.ui.orderOverview.adapters.AcceptedItemAdapter
import com.refreshing.ui.orderOverview.adapters.NewItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OrderOverviewFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var adapter: NewItemAdapter? = null
    var acceptedAdapter: AcceptedItemAdapter? = null
    var pd: ProgressDialog? = null
    var homeViewModel: OrderOverviewViewModel? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[OrderOverviewViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        pd = ProgressDialog(requireContext())
        pd?.setMessage("loading")
        sharedpreferences = requireContext().getSharedPreferences("state", Context.MODE_PRIVATE);



        _binding?.recyclerViewNewItems?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        adapter = NewItemAdapter()
        _binding?.recyclerViewNewItems?.adapter = adapter

        acceptedAdapter = AcceptedItemAdapter()
        _binding?.recyclerViewAcceptedItems?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        _binding?.recyclerViewAcceptedItems?.adapter = acceptedAdapter

        homeViewModel?.orders?.observe(viewLifecycleOwner, Observer {
            if (pd?.isShowing == true) {
                pd?.dismiss()
            }
            _binding?.newCountTextView?.text = it.data?.new?.size.toString()
            _binding?.acceptedCountTextView?.text = it.data?.accepted?.size.toString()
            Log.e("ItemsALI", it.data?.new.toString())

            it.data?.new?.let { it1 -> adapter?.submitNew(it1) }
            it.data?.accepted?.let { it1 -> acceptedAdapter?.submitNew(it1) }

//            _binding?.swiper?.isRefreshing = false
        })

        homeViewModel?.OrderErrors?.observe(viewLifecycleOwner, Observer {
            if (pd?.isShowing == true) {
                pd?.dismiss()
            }
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })

        context?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(
                mMessageReceiver,
                IntentFilter(UPLOAD_COMPLETE_ACTION)
            )
        };

//        _binding?.swiper?.setOnRefreshListener {
//            homeViewModel?.getOrders()
//            pd?.show()
//
//        }

        if(sharedpreferences?.getString("state","on").toString().equals("on")){
            _binding?.switchs?.isOn=true
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    pd?.show()
                    homeViewModel?.getOrders()
                }
            }
        }else
        {
            _binding?.switchs?.isOn=false
        }
        _binding?.switchs?.setOnToggledListener(object: OnToggledListener {
            override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
                if(isOn){
                    sharedpreferences?.edit()?.putString("state","on")?.commit()

                    FirebaseMessaging.getInstance().subscribeToTopic("muneshOrder")
                    pd?.show()
                    homeViewModel?.getOrders()

                    homeViewModel?.connectOrder("1")


                }else
                {

                    sharedpreferences?.edit()?.putString("state","off")?.commit()

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("muneshOrder")
                    acceptedAdapter?.submitNew(ArrayList())

                    adapter?.submitNew(ArrayList())
                    homeViewModel?.connectOrder("0")

                }
            }

        })

        return root
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // Get extra data included in the Intent

            val item = intent.getParcelableExtra<New>(UPLOAD_POST_EXTRA_NAME)
            item?.let { adapter?.addItem(it) }

            _binding?.recyclerViewNewItems?.scrollToPosition(0)
            _binding?.newCountTextView?.text = adapter?.itemCount.toString()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(mMessageReceiver) }

        _binding = null
    }
}