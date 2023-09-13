package com.example.ecommerce_shoes.ui.config

import android.view.KeyEvent
import android.widget.TextView
import com.example.ecommerce_shoes.R

abstract class ConfigListFragment : ConfigFormFragment() {

    private var callbackMainButtonUpdate: (Boolean) -> Unit = {}
    private var callbackBlockFields: (Boolean) -> Unit = {}
    private var callbackRemoveItem: (Boolean) -> Unit = {}

    fun callbacksToUpdateItem(
        mainButtonUpdate: (Boolean) -> Unit,
        blockFields: (Boolean) -> Unit,
        removeItem: (Boolean) -> Unit
    ) {
        callbackMainButtonUpdate = mainButtonUpdate
        callbackBlockFields = blockFields
        callbackRemoveItem = removeItem
    }

    override fun getLayoutResourceID() = R.layout.fragment_config_list
    override fun backEndFakeDelay(statusAction: Boolean, feedBackMessage: String) {}
    override fun blockFields(status: Boolean) {}
    override fun isMainButtonSending(status: Boolean) {}
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}