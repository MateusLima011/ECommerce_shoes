package com.example.ecommerce_shoes.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ContentConfigProfileBinding
import com.example.ecommerce_shoes.domain.User
import com.example.ecommerce_shoes.util.validate

class ConfigProfileActivity : FormActivity<ContentConfigProfileBinding>() {
    override fun createBinding(layoutInflater: LayoutInflater) =
        ContentConfigProfileBinding.inflate(layoutInflater)

    override fun initActivity() {
        setSupportActionBar(binding.toolbarConfigProfile.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.etNameProfile.validate({ it.length > 1 }, getString(R.string.invalid_name))
        binding.etNameProfile.setOnEditorActionListener(this)
        binding.ivProfile.setOnClickListener { callGallery() }
        binding.btSendProfile.setOnClickListener { mainAction() }

        val user = intent.getParcelableExtra<User>(User.KEY)
        if (user != null) {
            binding.etNameProfile.setText(user.name)
        }
    }

    override fun blockFields(status: Boolean) {
        binding.ivProfile.isEnabled = !status
        binding.etNameProfile.isEnabled = !status
        binding.btSendProfile.isEnabled = !status
    }

    override fun mainAction(view: View?) {
        showProxy(true)
        backEndFakeDelay()
        blockFields(true)
    }

    private fun showProxy(status: Boolean) {
        binding.proxyScreenConfigProfile.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btSendProfile.text = if (status) getString(R.string.config_profile_going)
        else getString(R.string.config_profile)
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                status = false,
                message = getString(R.string.invalid_config_profile)
            )
        }, 1000)
    }

    private fun callGallery() {
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT)
            .show()
    }
}