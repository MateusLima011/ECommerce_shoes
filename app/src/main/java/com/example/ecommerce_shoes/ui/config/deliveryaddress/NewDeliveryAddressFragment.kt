package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ConfigNewDeliveryAddressBinding
import com.example.ecommerce_shoes.ui.config.ConfigFormFragment

open class NewDeliveryAddressFragment : ConfigFormFragment() {

    private lateinit var binding: ConfigNewDeliveryAddressBinding

    companion object{
        const val PAGER_POS = 1
    }
    override fun getLayoutResourceID() = R.layout.config_new_delivery_address

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConfigNewDeliveryAddressBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun backEndFakeDelay(statusAction: Boolean, feedBackMessage: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                statusAction,
                feedBackMessage
            )
        }, 1000)
    }



    override fun blockFields(status: Boolean) {
        binding.etStreetNewAddress.isEnabled = !status
        binding.etNumberNewAddress.isEnabled = !status
        binding.etComplementNewAddress.isEnabled = !status
        binding.etZipCodeNewAddress.isEnabled = !status
        binding.etNeighborhoodNewAddress.isEnabled = !status
        binding.etCityNewAddress.isEnabled = !status
        binding.spStateNewAddress.isEnabled = !status
        binding.btNuAddress.isEnabled = !status
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btNuAddress.text =
            if (status)
                getString(R.string.add_delivery_address_going)
        else
            getString(R.string.add_delivery_address)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}