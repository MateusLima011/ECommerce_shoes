package com.example.ecommerce_shoes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.domain.AccountSettingItem

class AccountSettingsItemsAdapter(private val items: List<AccountSettingItem>) :
    RecyclerView.Adapter<AccountSettingsItemsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountSettingsItemsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_settings_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountSettingsItemsAdapter.ViewHolder, position: Int) {
        holder.setData(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvLabelSettingsAccount =
            itemView.findViewById<TextView>(R.id.tvLabelAccountSettings)
        private val tvDescriptionAccountSettings =
            itemView.findViewById<TextView>(R.id.tvDescriptionAccountSettings)

        init {
            tvDescriptionAccountSettings
            tvLabelSettingsAccount
        }

        fun setData(item: AccountSettingItem) {
            tvLabelSettingsAccount.text = item.label
            tvDescriptionAccountSettings.text = item.description
        }
    }
}