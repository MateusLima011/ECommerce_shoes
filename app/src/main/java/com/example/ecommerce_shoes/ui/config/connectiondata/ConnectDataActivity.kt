package com.example.ecommerce_shoes.ui.config.connectiondata

import com.example.ecommerce_shoes.ui.config.ConfigFormActivity
import com.example.ecommerce_shoes.ui.config.ConfigSectionsAdapter

class ConnectDataActivity : ConfigFormActivity() {
    override fun getSelectionAdapter() = ConfigSectionsAdapter(
        this,
        supportFragmentManager,
        lifecycle,
        FormEmailFragment(),
        FormPasswordFragment()
    )

}