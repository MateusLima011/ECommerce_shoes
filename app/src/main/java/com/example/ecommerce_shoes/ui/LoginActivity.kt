package com.example.ecommerce_shoes.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.ecommerce_shoes.MainActivity
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ActivityFormBinding
import com.example.ecommerce_shoes.databinding.ActivityLoginBinding
import com.example.ecommerce_shoes.databinding.ActivityMainBinding
import com.example.ecommerce_shoes.util.isValidEmail
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate
import com.google.android.material.snackbar.Snackbar
import kotlin.jvm.internal.Intrinsics.Kotlin

class LoginActivity : FormActivity(), TextView.OnEditorActionListener {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    private val _binding: ActivityFormBinding? get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarLogin.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.etEmail.validate({ it.isValidEmail() }, getString(R.string.invalid_email))
        binding.etPassword.validate({ it.isValidPassword() }, getString(R.string.invalid_password))

        initButton()

    }

    private fun initButton() {

        binding.btLogin.setOnClickListener {
            mainAction()
        }

        binding.etPassword.setOnEditorActionListener { view, actionId, event ->
            onEditorAction(view, actionId, event)
        }

        binding.tvForgotPassword.setOnClickListener {
            callForgotPasswordActivity()
        }

        binding.tvSignUp.setOnClickListener {
            callSingUpActivity()
        }

        binding.tvPolicyPrivacy.tvPrivacyPolicy.setOnClickListener {
            callPrivacyPolicyFragment()
        }
    }

    override fun onEditorAction(view: TextView, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            closeVirtualKeyBoard(view)
            mainAction()
            return true
        }
        return false
    }


    override fun blockFields(status: Boolean) {
        binding.etEmail.isEnabled = !status
        binding.etPassword.isEnabled = !status
        binding.btLogin.isEnabled = !status
    }

    override fun mainAction(view: View?) {
        blockFields(true)
        isMainButtonSending(true)
        showProxy(true)
        backEndFakeDelay()
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btLogin.text = if (status) getString(R.string.sign_in_going) else
            getString(R.string.sign_in)
    }

    private fun isSingInGoing(status: Boolean) {
        binding.btLogin.text =
            if (status) getString(R.string.sign_in_going)
            else
                getString(R.string.sign_in)
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isSingInGoing(false)
            blockFields(false)

            snackBarFeedback(binding.root, message = "Login invalido!", status = false)
        }, 1000)
    }


    private fun closeVirtualKeyBoard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun callForgotPasswordActivity() {
        Toast.makeText(this, "TODO: callForgotPasswordActivity", Toast.LENGTH_SHORT)
            .show()
    }

    private fun callSingUpActivity() {
        Toast.makeText(this, "TODO: callSingUpActivity", Toast.LENGTH_SHORT)
            .show()
    }

    private fun callPrivacyPolicyFragment() {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra(MainActivity.FRAGMENT_ID, R.id.item_privacy_policy)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        startActivity(intent)
    }

}