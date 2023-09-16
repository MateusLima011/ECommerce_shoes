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
import androidx.fragment.app.Fragment
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ConfigNewDeliveryAddressBinding
import com.example.ecommerce_shoes.domain.DeliveryAddress
import com.google.android.material.snackbar.Snackbar

class UpdateDeliveryAddressFragment : NewDeliveryAddressFragment() {

    private lateinit var binding: ConfigNewDeliveryAddressBinding

    override fun getLayoutResourceID() = R.layout.config_update_delivery_address
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConfigNewDeliveryAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNuAddress.text = getString(R.string.update_delivery_address)
        fillForm()
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

    override fun backEndFakeDelay(statusAction: Boolean, feedBackMessage: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            isMainButtonSending(false)

            snackBarFeedback(
                binding.root,
                statusAction,
                feedBackMessage
            )
        }, 1000)
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btNuAddress.text =
            if (status)
                getString(R.string.update_delivery_address_going)
            else
                getString(R.string.update_delivery_address)
    }

    override fun snackBarFeedback(viewContainer: ViewGroup, status: Boolean, message: String) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG)

        val iconColor =
            if (status) ContextCompat.getColor(requireActivity(), R.color.colorNavButton)
            else Color.RED

        val iconResource =
            if (status) R.drawable.ic_check_black_18dp else R.drawable.ic_close_black_18dp

        val img = ResourcesCompat.getDrawable(resources, iconResource, null)
        img!!.setBounds(0, 0, img.intrinsicWidth, img.intrinsicHeight)
        img.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP)

        val textView =
            snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

        val spannedText = SpannableString("     ${textView.text}")
        spannedText.setSpan(
            ImageSpan(img, ImageSpan.ALIGN_BOTTOM),
            0,
            1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.setText(spannedText, TextView.BufferType.SPANNABLE)

        snackBar.show()
    }

}