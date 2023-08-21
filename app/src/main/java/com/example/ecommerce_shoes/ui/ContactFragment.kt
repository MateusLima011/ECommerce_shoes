package com.example.ecommerce_shoes.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ecommerce_shoes.MainActivity
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.infoBlock.tvInfoBlock.text = getString(R.string.contact_frag_info)

        with(binding) {
            ivPhoneCities.setOnClickListener { phoneCallIntent(tvPhoneCities.text.toString()) }
            tvPhoneCities.setOnClickListener { phoneCallIntent(tvPhoneCities.text.toString()) }
            ivPhoneOtherRegions.setOnClickListener { phoneCallIntent(ivPhoneOtherRegions.toString()) }
            tvPhoneOtherRegions.setOnClickListener { phoneCallIntent(tvPhoneOtherRegions.toString()) }

            ivEmailOrders.setOnClickListener { mailToIntent(ivEmailOrders.toString()) }
            tvEmailOrders.setOnClickListener { mailToIntent(tvEmailOrders.toString()) }
            ivEmailAttendance.setOnClickListener { mailToIntent(ivEmailAttendance.toString()) }
            tvEmailAttendance.setOnClickListener { mailToIntent(tvEmailAttendance.toString()) }

            ivAddress.setOnClickListener {
                mapsRouteIntent(getString(R.string.contact_frag_address_formatted_to_google_maps))
            }
            tvAddress.setOnClickListener {
                mapsRouteIntent(getString(R.string.contact_frag_address_formatted_to_google_maps))
            }
        }
    }

    private fun phoneCallIntent(number: String) {
        val phoneNumber = number.replace(Regex("(\\s|\\)|\\(|-)"), "")
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    private fun mailToIntent(emailAddress: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))

        try {
            val intentChooser = Intent.createChooser(intent, getString(R.string.chooser_email_text))
            startActivity(intentChooser)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.info_email_app_install),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun mapsRouteIntent(address: String) {
        val location = Uri.encode(address)
        val navigation = "google.navigation:q=$location"

        val navigationUri = Uri.parse(navigation)
        val intent = Intent(Intent.ACTION_VIEW, navigationUri)
        intent.setPackage("com.google.android.apps.maps")

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.info_google_maps_install),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.updateToolbarTitleInFragment(R.string.contact_frag_title)
    }

}