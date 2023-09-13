package com.example.ecommerce_shoes.ui.config.connectiondata

import com.example.ecommerce_shoes.ui.config.ConfigFormActivity
import com.example.ecommerce_shoes.ui.config.ConfigSectionsAdapter

class ConnectDataActivity() : ConfigFormActivity() {

    override val titles = listOf("Email", "Senha")
    override fun getSelectionAdapter(): ConfigSectionsAdapter{
        val fragments = listOf(FormEmailFragment(), FormPasswordFragment())

        return ConfigSectionsAdapter(this, supportFragmentManager, lifecycle, fragments, titles)
    }

}