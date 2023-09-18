package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_shoes.data.DeliveryAddressesDataBase
import com.example.ecommerce_shoes.databinding.ConfigDeliveryAddressesListBinding
import com.example.ecommerce_shoes.ui.config.ConfigListFragment

class DeliveryAddressesListFragment : ConfigListFragment() {

    private lateinit var binding: ConfigDeliveryAddressesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConfigDeliveryAddressesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.rvDeliveryAddresses.setHasFixedSize(false)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvDeliveryAddresses.layoutManager = layoutManager

        val adapter = DeliveryAddressesListAdapter(this, DeliveryAddressesDataBase.getItems())
        binding.rvDeliveryAddresses.adapter = adapter
    }
}