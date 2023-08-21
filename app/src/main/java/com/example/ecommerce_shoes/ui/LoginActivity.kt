package com.example.ecommerce_shoes.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.ecommerce_shoes.MainActivity
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ActivityLoginBinding
import com.example.ecommerce_shoes.databinding.ContentForgotPasswordBinding
import com.example.ecommerce_shoes.util.isValidEmail
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate

open class LoginActivity : FormActivity<ActivityLoginBinding>(), TextView.OnEditorActionListener {
    override fun createBinding(layoutInflater: LayoutInflater) =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun initActivity() {
        setSupportActionBar(binding.toolbarLogin.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.etEmail.validate({ it.isValidEmail() }, getString(R.string.invalid_email))
        binding.etPassword.validate({ it.isValidPassword() }, getString(R.string.invalid_password))

        initViews()
    }


    private fun initViews() {

        binding.btLogin.setOnClickListener { mainAction() }

        binding.etPassword.setOnEditorActionListener(this)

        binding.tvForgotPassword.setOnClickListener { callForgotPasswordActivity() }

        binding.tvSignUp.setOnClickListener { callSingUpActivity() }

        binding.tvPolicyPrivacy.tvPrivacyPolicy.setOnClickListener {
            callPrivacyPolicyFragment()
        }
    }

    override fun blockFields(status: Boolean) {
        binding.etEmail.isEnabled = !status
        binding.etPassword.isEnabled = !status
        binding.btLogin.isEnabled = !status
    }

    override fun mainAction(view: View?) {
        blockFields(true)
        showProxy(true)
        isMainButtonSending(true)
        backEndFakeDelay()
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btLogin.text =
            if (status) getString(R.string.sign_in_going) else getString(R.string.sign_in)
    }

    private fun showProxy(status: Boolean) {
        binding.proxyScreenForm.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }

    private fun callForgotPasswordActivity() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                status = false,
                message = getString(R.string.invalid_login)
            )
        }, 1000)
    }

    private fun callSingUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun callPrivacyPolicyFragment() {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra(MainActivity.FRAGMENT_ID, R.id.item_privacy_policy)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        startActivity(intent)
    }

}