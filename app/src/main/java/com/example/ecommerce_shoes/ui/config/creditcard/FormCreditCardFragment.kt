package com.example.ecommerce_shoes.ui.config.creditcard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.FragmentConfigNewCreditCardBinding
import com.example.ecommerce_shoes.ui.config.ConfigFormFragment
import com.example.ecommerce_shoes.util.isValidCNPJ
import com.example.ecommerce_shoes.util.isValidCPF
import com.santalu.maskedittext.MaskEditText

class FormCreditCardFragment : ConfigFormFragment(), View.OnFocusChangeListener {

    private lateinit var binding: FragmentConfigNewCreditCardBinding
    override fun getLayoutResourceID() = R.layout.fragment_config_new_credit_card
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfigNewCreditCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btAddCreditCard.setOnClickListener { mainAction()}
        binding.etCreditCardOwnerReg.onFocusChangeListener = this
    }

    override fun backEndFakeDelay(statusAction: Boolean, feedBackMessage: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            isMainButtonSending(false)
            blockFields(false)

            snackBarFeedback(
                binding.root,
                statusAction,
                feedBackMessage
            )
        }, 1000)
    }

    override fun blockFields(status: Boolean) {
        binding.etCreditCardNumber.isEnabled = !status
        binding.spCreditCardEnterprise.isEnabled = !status
        binding.etCreditCardOwnerName.isEnabled = !status
        binding.etCreditCardOwnerReg.isEnabled = !status
        binding.etCreditCardExpiryYear.isEnabled = !status
        binding.spCreditCardExpiryMonth.isEnabled = !status
        binding.etCreditCardSecurityCode.isEnabled = !status
        binding.btAddCreditCard.isEnabled = !status
    }

    override fun isMainButtonSending(status: Boolean) {
        binding.btAddCreditCard.text = if (status) getString(R.string.add_credit_card_going)
        else getString(R.string.add_credit_card)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    private fun mainAction() {
        blockFields(true)
        isMainButtonSending(true)
        backEndFakeDelay(true, getString(R.string.invalid_credit_card))
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {

        var mask = ""
        val editText = v as MaskEditText
        val pattern = Regex("[^0-9]")
        val content = editText
            .text
            .toString()
            .replace(pattern,"")

        if (!hasFocus){
            if (content.isValidCPF()){
                mask = "###.###.###-##"
            }
            else if (content.isValidCNPJ()){
                mask = "##.###.###/####-##"
            }
        }

        editText.mask = mask
        editText.setText(content)
    }
}