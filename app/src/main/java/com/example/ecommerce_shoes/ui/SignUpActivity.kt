package com.example.ecommerce_shoes.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.example.ecommerce_shoes.MainActivity
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ContentSignUpBinding
import com.example.ecommerce_shoes.util.isValidEmail
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate

class SignUpActivity : FormActivity<ContentSignUpBinding>(),
    KeyboardUtils.OnSoftInputChangedListener {

    override fun createBinding(layoutInflater: LayoutInflater) =
        ContentSignUpBinding.inflate(layoutInflater)

    override fun initActivity() {
        setSupportActionBar(binding.toolbarSignUp.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        KeyboardUtils.registerSoftInputChangedListener(this, this)

        binding.etEmail.validate({ it.isValidEmail() }, getString(R.string.invalid_email))
        binding.etPassword.validate({ it.isValidPassword() }, getString(R.string.invalid_password))
        binding.etConfirmPassword.validate({
            (binding.etPassword.text.isNotEmpty() && it.equals(
                binding.etPassword.text.toString()
            )) || binding.etPassword.text.isEmpty()
        }, getString(R.string.invalid_confirmed_password))

        binding.etConfirmPassword.setOnEditorActionListener(this)

        initViews()
    }

    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(window)
        super.onDestroy()
    }

    private fun initViews() {
        binding.privacyPolicySignUp.tvPrivacyPolicy.setOnClickListener {
            callPrivacyPolicyFragment()
        }
        binding.btSignUp.setOnClickListener { mainAction() }
    }

    override fun blockFields(status: Boolean) {
        binding.etEmail.isEnabled = !status
        binding.etPassword.isEnabled = !status
        binding.etConfirmPassword.isEnabled = !status
        binding.btSignUp.isEnabled = !status
    }

    private fun showProxy(status: Boolean) {
        binding.proxyScreenSignUp.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }

    override fun mainAction(view: View?) {
        blockFields(true)
        isMainButtonSending(true)
        showProxy(true)
        backEndFakeDelay()
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                status = false,
                message = getString(R.string.invalid_sign_up_email)
            )
        }, 1000)
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btSignUp.text = if (status) getString(R.string.sign_up_going)
        else getString(R.string.sign_up)
    }

    override fun onSoftInputChanged(height: Int) {
        changePrivacyPolicyConstraints(KeyboardUtils.isSoftInputVisible(this))
    }

    private fun changePrivacyPolicyConstraints(isKeyBoardOpened: Boolean) {

        val privacyId = R.id.tvPolicyPrivacy
        val parent = binding.privacyPolicySignUp.tvPrivacyPolicy.parent as ConstraintLayout
        val constraintSet = ConstraintSet()

        constraintSet.clone(parent)

        if (isKeyBoardOpened || ScreenUtils.isLandscape()) {

            constraintSet.connect(
                privacyId, ConstraintSet.TOP, R.id.bt_sign_up, ConstraintSet.BOTTOM,
                (12 * ScreenUtils.getScreenDensity()).toInt()
            )
        } else {
            constraintSet.connect(
                privacyId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
            )
        }

        constraintSet.applyTo(parent)
    }

    private fun callPrivacyPolicyFragment() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.FRAGMENT_ID, R.id.item_privacy_policy)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}