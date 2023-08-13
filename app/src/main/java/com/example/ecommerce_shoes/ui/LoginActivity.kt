package com.example.ecommerce_shoes.ui

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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ActivityLoginBinding
import com.example.ecommerce_shoes.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.jvm.internal.Intrinsics.Kotlin

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarLogin.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupEmailField()
        setupPasswordField()

        binding.btLogin.setOnClickListener {
            login()
        }

        binding.etPassword.setOnEditorActionListener { view, actionId, event ->
            onEditorAction(view, actionId, event)
        }
    }

    private fun onEditorAction(view: TextView, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            closeVirtualKeyBoard(view)
            login()
            return true
        }
        return false
    }

    private fun setupEmailField() {

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(content: Editable) {

                val message = getString(R.string.invalid_email)

                binding.etEmail.error =
                    if (content.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(content).matches())
                        null
                    else
                        message
            }

            override fun beforeTextChanged(
                content: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                content: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
    }

    private fun setupPasswordField() {
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(content: Editable?) {

                val message = getString(R.string.invalid_password)

                if (content != null) {
                    binding.etPassword.error =
                        if (content.length > 5)
                            null
                        else
                            message
                }
            }

            override fun beforeTextChanged(
                content: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                content: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
    }

    private fun showProxy(status: Boolean) {
        binding.flProxyContainer.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun snackBarFeedback(viewContainer: ViewGroup, status: Boolean, message: String) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG)

        val snackBarView = snackBar.view
        val textView = snackBarView.findViewById(
            com.google.android.material.R.id.snackbar_text
        ) as TextView

        val iconResource =
            if (status) R.drawable.ic_check_black_18dp else R.drawable.ic_close_black_18dp

        val img = ResourcesCompat.getDrawable(resources, iconResource, null)
        img!!.setBounds(0, 0, img.intrinsicWidth, img.intrinsicHeight)

        val iconColor =
            if (status) ContextCompat.getColor(this, R.color.colorNavButton)
            else
                Color.RED
        img.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP)

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

    private fun blockFields(status: Boolean) {
        binding.etEmail.isEnabled = !status
        binding.etPassword.isEnabled = !status
        binding.btLogin.isEnabled = !status
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
        }, 1000)
    }

    private fun login() {
        blockFields(true)
        isSingInGoing(true)
        showProxy(true)
        backEndFakeDelay()
    }

    private fun closeVirtualKeyBoard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}