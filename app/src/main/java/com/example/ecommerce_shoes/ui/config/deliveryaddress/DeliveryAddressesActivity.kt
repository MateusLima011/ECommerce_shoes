package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.view.MenuItem
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce_shoes.databinding.ActivityTabsUserConfigBinding
import com.example.ecommerce_shoes.ui.config.ConfigFormActivity

class DeliveryAddressesActivity() : ConfigFormActivity() {

    private val binding: ActivityTabsUserConfigBinding? = null

    override val titles = listOf("Endereços", "Novo Endereço")
    override fun getSelectionAdapter(): FragmentStateAdapter {
        val fragments = listOf(DeliveryAddressHostFragment(), NewDeliveryAddressFragment())

        return DeliveryAddressesSectionsAdapter(
            this,
            supportFragmentManager,
            lifecycle,
            fragments,
            titles
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val fragmentInStack = supportFragmentManager.backStackEntryCount

        if (fragmentInStack > 0 && isNewDeliveryAddressFormNotInScreen()){
            supportFragmentManager.popBackStack()
        }
        else{
            finish()
        }
    }

    private fun isNewDeliveryAddressFormNotInScreen(): Boolean = binding?.viewPager?.currentItem !=
            NewDeliveryAddressFragment.PAGER_POS

}