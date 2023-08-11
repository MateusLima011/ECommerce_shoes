package com.example.ecommerce_shoes.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerce_shoes.MainActivity
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvInstagram.setOnClickListener {
            openNetwork(
                "com.instagram.android",
                "http://instagram.com/_u/cbf_futebol",
                "http://instagram.com/cbf_futebol"
            )
        }
        binding.tvFacebook.setOnClickListener {
            openNetwork(
                "com.facebook.katana",
                "fb://facewebmodal/f?href=https://www.facebook.com/thiengoCalopsita",
                "https://www.facebook.com/thiengoCalopsita"
            )
        }
        binding.tvTwitter.setOnClickListener {
            openNetwork(
                "com.twitter.android",
                "https://twitter.com/thiengoCalops",
                "https://twitter.com/thiengoCalops"
            )
        }
        binding.tvYoutube.setOnClickListener {
            openNetwork(
                "com.google.android.youtube",
                "https://www.youtube.com/user/thiengoCalopsita",
                "https://www.youtube.com/user/thiengoCalopsita"
            )
        }
        binding.tvLinkedin.setOnClickListener {
            openNetwork(
                "com.linkedin.android",
                "https://www.linkedin.com/in/vin%C3%ADcius-thiengo-5179b180",
                "https://www.linkedin.com/in/vin%C3%ADcius-thiengo-5179b180"
            )
        }
    }

    private fun openNetwork(
        appPackage: String,
        appAddress: String,
        webAddress: String
    ) {

        val uri = Uri.parse(appAddress)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        intent.setPackage(appPackage)

        try {
            requireActivity().startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webAddress)))
        }
    }

    override fun onResume() {
        super.onResume()

        val mainActivity = activity as? MainActivity
        mainActivity?.updateToolbarTitleInFragment(R.string.about_frag_title)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

