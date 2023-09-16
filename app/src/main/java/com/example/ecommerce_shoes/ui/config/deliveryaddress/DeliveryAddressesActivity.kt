package com.example.ecommerce_shoes.ui.config.deliveryaddress

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce_shoes.ui.config.ConfigFormActivity

class DeliveryAddressesActivity() : ConfigFormActivity() {

    override val titles = listOf("Endereços", "Novo Endereço")
    override fun getSelectionAdapter(): FragmentStateAdapter {
        val fragments = listOf(DeliveryAddressesListFragment(), NewDeliveryAddressFragment())

        return DeliveryAddressesSectionsAdapter(
            this,
            supportFragmentManager,
            lifecycle,
            fragments,
            titles
        )
    }


}