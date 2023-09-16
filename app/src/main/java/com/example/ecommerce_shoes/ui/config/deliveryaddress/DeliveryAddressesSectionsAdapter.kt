package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce_shoes.ui.config.ConfigFormFragment

class DeliveryAddressesSectionsAdapter(
    val context: Context, fragManager: FragmentManager, lifecycle: Lifecycle,
    private val fragmentsList: List<ConfigFormFragment>,
    private val titleList: List<String>
) : FragmentStateAdapter(fragManager, lifecycle) {

    companion object{
        const val TOTAL_PAGES = 2
    }
    override fun getItemCount() = TOTAL_PAGES

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }
}