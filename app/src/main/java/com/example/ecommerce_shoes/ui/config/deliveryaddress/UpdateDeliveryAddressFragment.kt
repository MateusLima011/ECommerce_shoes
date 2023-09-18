package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ConfigNewDeliveryAddressBinding
import com.example.ecommerce_shoes.domain.DeliveryAddress
import com.example.ecommerce_shoes.util.visible
import com.google.android.material.snackbar.Snackbar

class UpdateDeliveryAddressFragment : NewDeliveryAddressFragment() {

    private val binding by lazy { ConfigNewDeliveryAddressBinding.inflate(layoutInflater) }

    override fun getLayoutResourceID() = R.layout.config_new_delivery_address
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.tvTitleAt.visible()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNuAddress.text = getString(R.string.update_delivery_address)
        fillForm()
        binding.btNuAddress.setOnClickListener { mainAction() }
    }

    private fun fillForm() {
        val address = requireArguments().getParcelable<DeliveryAddress>(
            DeliveryAddress.KEY
        )
        binding.etStreetNewAddress.setText(address?.street)
        binding.etNumberNewAddress.setText(address?.number.toString())
        binding.etComplementNewAddress.setText(address?.complement)
        binding.etZipCodeNewAddress.setText(address?.zipCode)
        binding.etNeighborhoodNewAddress.setText(address?.neighborhood)
        binding.etCityNewAddress.setText(address?.city)
        binding.spStateNewAddress.setSelection(address!!.state)
    }

    private fun mainAction(){
        showProxy(true)
        backEndFakeDelay()
        isMainButtonSending(true)
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)

            snackBarFeedback(
                binding.root,
                false,
                getString(R.string.invalid_delivery_address)
            )
        }, 1000)
    }

    private fun isMainButtonSending(status: Boolean) {
        binding.btNuAddress.text =
            if (status)
                getString(R.string.update_delivery_address_going)
            else
                getString(R.string.update_delivery_address)
    }

    private fun showProxy(status: Boolean) {
        binding.proxyScreeDelivery.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }

}