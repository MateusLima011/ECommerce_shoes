package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_shoes.data.DeliveryAddressesDataBase
import com.example.ecommerce_shoes.databinding.ConfigDeliveryAddressesListBinding
import com.example.ecommerce_shoes.ui.config.ConfigListFragment

class DeliveryAddressesListFragment : ConfigListFragment() {

    private lateinit var binding: ConfigDeliveryAddressesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConfigDeliveryAddressesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.rvDeliveryAddresses.setHasFixedSize(false)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvDeliveryAddresses.layoutManager = layoutManager

        val adapter = DeliveryAddressesListAdapter(this, DeliveryAddressesDataBase.getItems())
        binding.rvDeliveryAddresses.adapter = adapter
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
        TODO("Not yet implemented")
    }

    override fun isMainButtonSending(status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}