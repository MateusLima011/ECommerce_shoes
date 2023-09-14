package com.example.ecommerce_shoes.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

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


private val weightCPF = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)
private val weightCNPJ = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

fun String.isValidCPF(): Boolean {
    if (this.length != 11)
        return false

    val digit1 = calculateCPFOrCNPJDigit(this.substring(0, 9), weightCPF)
    val digit2 = calculateCPFOrCNPJDigit(this.substring(0, 9) + digit1, weightCPF)

    return this == (this.substring(0, 9) + digit1.toString() + digit2.toString())
}

fun String.isValidCNPJ(): Boolean {
    if (this.length != 14)
        return false

    val digit1 = calculateCPFOrCNPJDigit(this.substring(0, 12), weightCNPJ)
    val digit2 = calculateCPFOrCNPJDigit(this.substring(0, 12) + digit1, weightCNPJ)

    return this == (this.substring(0, 12) + digit1.toString() + digit2.toString())
}

private fun calculateCPFOrCNPJDigit(str: String, weight: IntArray): Int {
    var sum = 0
    var index = str.length - 1
    var digit: Int

    while (index >= 0) {
        digit = str[index].toString().toIntOrNull() ?: 0
        sum += digit * weight[weight.size - str.length + index]
        index--
    }

    sum = 11 - sum % 11

    return if (sum > 9)
        0
    else
        sum
}


