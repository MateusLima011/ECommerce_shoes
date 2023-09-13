package com.example.ecommerce_shoes.ui.config.creditcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.data.CreditCardsDataBase
import com.example.ecommerce_shoes.databinding.FragmentConfigListBinding
import com.example.ecommerce_shoes.ui.config.ConfigListFragment
import com.example.ecommerce_shoes.util.gone
import com.example.ecommerce_shoes.util.visible

class CreditCardsListFragment : ConfigListFragment() {

    private lateinit var binding: FragmentConfigListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfigListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = CreditCardsDataBase.getItems()
        val adapter = CreditCardsListAdapter(this, items) { isProgress ->
            with(binding.progressBar) {
                if (isProgress) visible()
                else gone()
            }
        }

        binding.rvCreditCards.adapter = adapter
        binding.rvCreditCards.layoutManager = LinearLayoutManager(requireContext())
    }

}