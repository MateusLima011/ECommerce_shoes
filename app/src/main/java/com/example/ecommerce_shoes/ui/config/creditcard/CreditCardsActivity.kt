package com.example.ecommerce_shoes.ui.config.creditcard

import com.example.ecommerce_shoes.ui.config.ConfigFormActivity
import com.example.ecommerce_shoes.ui.config.ConfigSectionsAdapter

class CreditCardsActivity : ConfigFormActivity() {

    override  val titles = listOf("Cartões", "Novo Cartão")
    override fun getSelectionAdapter(): ConfigSectionsAdapter {
        val fragments = listOf(CreditCardsListFragment(), FormCreditCardFragment())

        return ConfigSectionsAdapter(this, supportFragmentManager, lifecycle, fragments, titles)
    }
}
