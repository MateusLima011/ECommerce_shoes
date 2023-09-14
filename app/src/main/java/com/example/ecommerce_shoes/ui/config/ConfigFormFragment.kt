package com.example.ecommerce_shoes.ui.config

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.FragmentConfigListBinding
import com.example.ecommerce_shoes.databinding.FragmentConfigPasswordBinding
import com.example.ecommerce_shoes.databinding.FragmentContactBinding
import com.example.ecommerce_shoes.ui.config.connectiondata.FormPasswordFragment
import com.example.ecommerce_shoes.util.PasswordDialogCaller
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate
import com.google.android.material.snackbar.Snackbar

abstract class ConfigFormFragment : Fragment(), TextView.OnEditorActionListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResourceID(), container, false)
    }

    abstract fun getLayoutResourceID(): Int
    abstract fun backEndFakeDelay(statusAction: Boolean, feedBackMessage: String)
    abstract fun blockFields(status: Boolean)
    abstract fun isMainButtonSending(status: Boolean)
    protected fun snackBarFeedback(viewContainer: ViewGroup, status: Boolean, message: String) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG)

        val iconColor =
            if (status) ContextCompat.getColor(requireActivity(), R.color.colorNavButton)
            else Color.RED

        val iconResource =
            if (status) R.drawable.ic_check_black_18dp else R.drawable.ic_close_black_18dp

        val img = ResourcesCompat.getDrawable(resources, iconResource, null)
        img!!.setBounds(0, 0, img.intrinsicWidth, img.intrinsicHeight)
        img.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP)

        val textView =
            snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

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
}