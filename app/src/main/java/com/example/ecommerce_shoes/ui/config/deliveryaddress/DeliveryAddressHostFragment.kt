package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.ui.config.ConfigFormFragment

class DeliveryAddressHostFragment: ConfigFormFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.fragment_config_delivery_address_host, container, false)

        if (savedInstanceState == null){
            val transaction = requireActivity()
                .supportFragmentManager
                .beginTransaction()

            transaction
                .replace(R.id.fl_root, DeliveryAddressesListFragment())
                .commit()
        }
        return view
    }

    override fun getLayoutResourceID() = 0
    override fun backEndFakeDelay(statusAction: Boolean, feedBackMessage: String) {
        TODO("Not yet implemented")
    }

    override fun blockFields(status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isMainButtonSending(status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}