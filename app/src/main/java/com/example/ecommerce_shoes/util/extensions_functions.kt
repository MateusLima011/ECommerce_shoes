package com.example.ecommerce_shoes.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText

private fun EditText.afterTextChanged(invokeValidation: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {


        override fun afterTextChanged(content: Editable?) {
            invokeValidation(content.toString())
        }

        override fun beforeTextChanged(
            content: CharSequence?, start: Int, count: Int, after: Int
        ) {
        }

        override fun onTextChanged(content: CharSequence?, start: Int, before: Int, count: Int) {}

    })

}

fun EditText.validate(validator: (String) -> Boolean, message: String) {
    this.afterTextChanged { this.error = if (validator(it)) null else message }
}

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean = this.length > 5
