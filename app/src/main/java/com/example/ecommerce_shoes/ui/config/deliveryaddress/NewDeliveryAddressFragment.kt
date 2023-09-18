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

    private val binding by lazy { ConfigNewDeliveryAddressBinding.inflate(layoutInflater) }

    companion object {
        const val PAGER_POS = 1
    }

    override fun getLayoutResourceID() = R.layout.config_new_delivery_address

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btNuAddress.setOnClickListener { mainAction() }
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                false,
                getString(R.string.invalid_delivery_address)
            )
        }, 1000)
    }

    private fun blockFields(status: Boolean) {
        binding.etStreetNewAddress.isEnabled = !status
        binding.etNumberNewAddress.isEnabled = !status
        binding.etComplementNewAddress.isEnabled = !status
        binding.etZipCodeNewAddress.isEnabled = !status
        binding.etNeighborhoodNewAddress.isEnabled = !status
        binding.etCityNewAddress.isEnabled = !status
        binding.spStateNewAddress.isEnabled = !status
        binding.btNuAddress.isEnabled = !status
    }

    private fun mainAction() {
        blockFields(true)
        backEndFakeDelay()
        showProxy(true)
        isMainButtonSending(true)
    }

    private fun isMainButtonSending(status: Boolean) {
        binding.btNuAddress.text =
            if (status)
                getString(R.string.add_delivery_address_going)
            else
                getString(R.string.add_delivery_address)
    }

    private fun showProxy(status: Boolean) {
        binding.proxyScreeDelivery.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }
}