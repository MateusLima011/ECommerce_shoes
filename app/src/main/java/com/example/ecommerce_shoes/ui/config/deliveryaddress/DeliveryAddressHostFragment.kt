package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerce_shoes.R

class DeliveryAddressHostFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.delivery_address_host, container, false)

        if (savedInstanceState == null){
            val transaction = requireActivity()
                .supportFragmentManager
                .beginTransaction()

            transaction
                .replace(R.id.flRoot, DeliveryAddressesListFragment())
                .commit()
        }
        return view
    }
}