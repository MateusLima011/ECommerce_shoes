package com.example.ecommerce_shoes.data

import android.content.Context
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.domain.AccountSettingItem
import com.example.ecommerce_shoes.ui.ConfigProfileActivity
import com.example.ecommerce_shoes.ui.config.connectiondata.ConnectDataActivity
import com.example.ecommerce_shoes.ui.config.creditcard.CreditCardsActivity
import com.example.ecommerce_shoes.ui.config.deliveryaddress.DeliveryAddressesActivity

class AccountSettingsItemsDataBase {
    companion object {

        fun getItems(context: Context) = listOf(
            AccountSettingItem(
                context.getString(R.string.setting_item_profile),
                context.getString(R.string.setting_item_profile_desc),
                ConfigProfileActivity::class.java
            ),
            AccountSettingItem(
                context.getString(R.string.setting_item_login),
                context.getString(R.string.setting_item_login_desc),
                ConnectDataActivity::class.java
            ),
            AccountSettingItem(
                context.getString(R.string.setting_item_address),
                context.getString(R.string.setting_item_address_desc),
                DeliveryAddressesActivity::class.java
            ),
            AccountSettingItem(
                context.getString(R.string.setting_item_credit_cards),
                context.getString(R.string.setting_item_credit_cards_desc),
                CreditCardsActivity::class.java
            )
        )
    }
}