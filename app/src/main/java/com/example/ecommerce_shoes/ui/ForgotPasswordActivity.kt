package com.example.ecommerce_shoes.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ContentForgotPasswordBinding
import com.example.ecommerce_shoes.util.isValidEmail
import com.example.ecommerce_shoes.util.validate

class ForgotPasswordActivity : FormActivity<ContentForgotPasswordBinding>() {
    override fun createBinding(layoutInflater: LayoutInflater) =
        ContentForgotPasswordBinding.inflate(layoutInflater)

    override fun initActivity() {
        binding.etEmail.validate({ it.isValidEmail() }, getString(R.string.invalid_email))
        setSupportActionBar(binding.toolbarForgotPassword.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    private fun initViews() {
        binding.btnRecoverPassword.setOnClickListener { mainAction() }
        binding.etEmail.setOnEditorActionListener(this)
        binding.infoBlockForgotPassword.tvInfoBlock.text = getString(R.string.forgot_password_info)
    }

    override fun blockFields(status: Boolean) {
        binding.etEmail.isEnabled = !status
        binding.btnRecoverPassword.isEnabled = !status
    }

    override fun mainAction(view: View?) {
        blockFields(true)
        isMainButtonSending(true)
        showProxy(true)
        backEndFakeDelay()
    }

    private fun showProxy(status: Boolean) {
        binding.proxyScreenForm.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                status = false,
                message = getString(R.string.invalid_login_email)
            )
        }, 1000)
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btnRecoverPassword.text = if (status) getString(R.string.recover_password_going)
        else getString(R.string.recover_password)
    }
}