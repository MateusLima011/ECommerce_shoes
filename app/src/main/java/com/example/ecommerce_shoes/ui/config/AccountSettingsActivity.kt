package com.example.ecommerce_shoes.ui.config

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.data.AccountSettingsItemsDataBase
import com.example.ecommerce_shoes.databinding.ActivityAccountSettingsBinding
import com.example.ecommerce_shoes.domain.User

class AccountSettingsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAccountSettingsBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appBarAccountSettings.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(User.KEY)
        binding.tvUserConnected.text = user?.let {
            getString(R.string.connected) + " " + it.name
        }
        initItems()
    }

    private fun initItems() {
        binding.rvAccountSettingsItems.setHasFixedSize(false)
        val layoutManager = LinearLayoutManager(this)
        binding.rvAccountSettingsItems.layoutManager = layoutManager

        val divider = DividerItemDecoration(this, layoutManager.orientation)
        ContextCompat.getDrawable(this, R.drawable.light_grey_divider_line)
            ?.let { divider.setDrawable(it) }!!

        binding.rvAccountSettingsItems.addItemDecoration(divider)
        binding.rvAccountSettingsItems.adapter =
            AccountSettingsListAdapter(AccountSettingsItemsDataBase.getItems(this))
    }

    fun getUser() = intent.getParcelableExtra<User>(User.KEY)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}