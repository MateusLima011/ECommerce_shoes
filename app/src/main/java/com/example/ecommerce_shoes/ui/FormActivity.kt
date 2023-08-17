package com.example.ecommerce_shoes.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

abstract class FormActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater).apply { setContentView(root) }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarLogin.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        window.setBackgroundDrawableResource(R.drawable.bg_activity)
    }

    protected fun showProxy(status: Boolean) {
        binding.proxyScreen.flProxyContainer.visibility = if (status) View.VISIBLE else View.GONE
    }

    protected fun snackBarFeedback(viewContainer: ViewGroup, status: Boolean, message: String) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG)

        val iconResource =
            if (status) R.drawable.ic_check_black_18dp else R.drawable.ic_close_black_18dp

        val img = ResourcesCompat.getDrawable(resources, iconResource, null)
        img!!.setBounds(0, 0, img.intrinsicWidth, img.intrinsicHeight)

        val iconColor =
            if (status) ContextCompat.getColor(this, R.color.colorNavButton)
            else
                Color.RED
        img.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP)

        val textView = snackBar.view.findViewById(
            com.google.android.material.R.id.snackbar_text
        ) as TextView

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

    abstract fun blockFields(status: Boolean)

    abstract fun mainAction(view: View? = null)

    abstract fun isMainButtonSending(status: Boolean)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}