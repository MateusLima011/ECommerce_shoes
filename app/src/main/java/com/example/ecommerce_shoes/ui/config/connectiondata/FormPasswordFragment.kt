package com.example.ecommerce_shoes.ui.config.connectiondata

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.ColorUtils
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.FragmentConfigPasswordBinding
import com.example.ecommerce_shoes.ui.config.ConfigFormFragment
import com.example.ecommerce_shoes.util.PasswordDialogCaller
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate

class FormPasswordFragment : ConfigFormFragment(), PasswordDialogCaller,
    TextView.OnEditorActionListener {

    private lateinit var binding: FragmentConfigPasswordBinding
    override fun getLayoutResourceID() = R.layout.fragment_config_password

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfigPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btUpdatePassword.setOnClickListener { callPasswordDialog() }

        binding.etNewPassword.validate(
            { it.isValidPassword() },
            getString(R.string.invalid_password)
        )

        binding.etNewPasswordConfirm.validate({
            (binding.etNewPassword.text.isNotEmpty() && it == binding.etNewPassword.text.toString())
                    || binding.etNewPassword.text.isEmpty()
        }, getString(R.string.invalid_confirmed_password))
        binding.etNewPasswordConfirm.setOnEditorActionListener(this)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mainAction()
            return true
        }
        return false
    }

    private fun backEndFakeDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            showProxy(false)
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                false,
                getString(R.string.invalid_password)
            )
        }, 1000)
    }

    private fun blockFields(status: Boolean) {
        binding.etNewPassword.isEnabled = !status
        binding.etNewPasswordConfirm.isEnabled = !status
        binding.btUpdatePassword.isEnabled = !status
    }

    private fun isMainButtonSending(status: Boolean) {
        binding.btUpdatePassword.text = if (status) getString(R.string.update_password_going)
        else getString(R.string.update_password)
    }


    private fun showProxy(status: Boolean) {
        binding.proxyScreenPasswordConfig.flProxyContainer.visibility =
            if (status) View.VISIBLE else View.GONE
    }

    private fun mainAction() {
        blockFields(true)
        isMainButtonSending(true)
        showProxy(true)
        backEndFakeDelay()
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
