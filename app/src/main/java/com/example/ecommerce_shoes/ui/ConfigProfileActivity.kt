package com.example.ecommerce_shoes.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.bumptech.glide.Glide
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ContentConfigProfileBinding
import com.example.ecommerce_shoes.domain.User
import com.example.ecommerce_shoes.util.validate
import com.github.dhaval2404.imagepicker.ImagePicker


class ConfigProfileActivity : FormActivity<ContentConfigProfileBinding>(),
    KeyboardUtils.OnSoftInputChangedListener {

    override fun createBinding(layoutInflater: LayoutInflater) =
        ContentConfigProfileBinding.inflate(layoutInflater)

    override fun initActivity() {
        setSupportActionBar(binding.toolbarConfigProfile.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.etNameProfile.validate({ it.length > 1 }, getString(R.string.invalid_name))
        binding.etNameProfile.setOnEditorActionListener(this)
        binding.rivProfile.setOnClickListener { callGallery() }
        binding.btSendProfile.setOnClickListener { mainAction() }

        val user = intent.getParcelableExtra<User>(User.KEY)
        if (user != null) {
            binding.etNameProfile.setText(user.name)
        }
        binding.rivProfile.setImageResource(user?.image ?: R.drawable.profile_hint)
    }

    override fun blockFields(status: Boolean) {
        binding.rivProfile.isEnabled = !status
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
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            val imageUri: Uri = data?.data ?: return

            Glide.with(this)
                .load(imageUri)
                .into(binding.rivProfile)
        }
    }

    override fun onSoftInputChanged(height: Int) {
        TODO("Not yet implemented")
    }

}