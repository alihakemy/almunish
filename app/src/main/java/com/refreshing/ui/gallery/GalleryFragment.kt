package com.refreshing.ui.gallery

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.refreshing.databinding.FragmentGalleryBinding
import com.refreshing.ui.gallery.fragments.Yesterday
import com.refreshing.ui.gallery.fragments.today


class GalleryFragment : Fragment() {

    private var binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding?.root!!


        val fm: FragmentManager? = activity?.supportFragmentManager
        val sa = fm?.let { ViewStateAdapter(it, lifecycle) }

        binding?.viewpager?.adapter=sa



        binding?.tabLayout?.let {
            binding?.viewpager?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->

                    if(position==0){
                        tab.text =context?.getString(com.refreshing.R.string.today)

                    }else{
                        tab.text = getString(com.refreshing.R.string.yesterday)

                    }
                }.attach()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private class ViewStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun createFragment(position: Int): Fragment {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            return if (position == 0) {
                today()
            } else Yesterday()
        }

        override fun getItemCount(): Int {
            // Hardcoded, use lists
            return 2
        }
    }
}