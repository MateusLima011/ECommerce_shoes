package com.example.ecommerce_shoes.ui.config.connectiondata

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.ColorUtils
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.FragmentConfigEmailBinding
import com.example.ecommerce_shoes.ui.config.ConfigFormFragment
import com.example.ecommerce_shoes.util.PasswordDialogCaller
import com.example.ecommerce_shoes.util.isValidEmail
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate

class FormEmailFragment : ConfigFormFragment(), PasswordDialogCaller {

    private lateinit var binding: FragmentConfigEmailBinding
    override fun getLayoutResourceID() = R.layout.fragment_config_email

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfigEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btUpdateEmailLogin.setOnClickListener { callPasswordDialog() }

        binding.etCurrentEmail.validate({ it.isValidEmail() }, getString(R.string.invalid_email))
        binding.etNewEmail.validate({ it.isValidEmail() }, getString(R.string.invalid_email))
        binding.etNewEmailConfirm.validate({
            binding.etNewEmail.text.isNotEmpty() && it == binding.etNewEmail.text.toString()
                    || binding.etNewEmail.text.isEmpty()
        }, getString(R.string.invalid_confirmed_email))
        binding.etNewEmailConfirm.setOnEditorActionListener(this)
    }

    private fun showProxy(status: Boolean) {
        binding.proxyScreenEmailConfig.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }

    override fun backEndFakeDelay(statusAction: Boolean, feedBackMessage: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                statusAction,
                feedBackMessage
            )
        }, 1000)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    private fun mainAction() {
        blockFields(true)
        isMainButtonSending(true)
        showProxy(true)
        backEndFakeDelay(true, getString(R.string.invalid_password))
    }

    override fun blockFields(status: Boolean) {
        binding.etCurrentEmail.isEnabled = !status
        binding.etNewEmail.isEnabled = !status
        binding.etNewEmailConfirm.isEnabled = !status
        binding.btUpdateEmailLogin.isEnabled = !status
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btUpdateEmailLogin.text = if (status) getString(R.string.update_email_login_going)
        else getString(R.string.update_email_login)
    }

    override fun callPasswordDialog() {
        val dialogView = requireActivity().layoutInflater.inflate(R.layout.dialog_password, null)

        val builder = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .setPositiveButton(R.string.dialog_password_go) { _, _ -> mainAction() }
            .setNegativeButton(R.string.dialog_password_cancel) { dialog, _ -> dialog.cancel() }
            .setCancelable(false)

        val dialog = builder.create()

        val etPassword = dialogView.findViewById<EditText>(R.id.etPassword)
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        etPassword?.validate(
            { it.isValidPassword() },
            getString(R.string.invalid_password)
        )

        etPassword?.setOnEditorActionListener { _, _, _ ->
            dialog.cancel()
            mainAction()
            false
        }

        positiveButton?.setTextColor(ColorUtils.getColor(R.color.colorText))
        negativeButton?.setTextColor(ColorUtils.getColor(R.color.colorText))

        dialog.show()
    }
}