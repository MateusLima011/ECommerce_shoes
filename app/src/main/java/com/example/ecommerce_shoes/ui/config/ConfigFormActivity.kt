package com.example.ecommerce_shoes.ui.config

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ActivityTabsUserConfigBinding
import com.google.android.material.tabs.TabLayoutMediator

abstract class ConfigFormActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTabsUserConfigBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    protected abstract val titles: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        window.setBackgroundDrawableResource(R.drawable.bg_activity)

        val viewpager2 = binding.viewPager
        val tabLayout = binding.tabs

        val selectionsAdapter = getSelectionAdapter()
        viewpager2.adapter = selectionsAdapter

        TabLayoutMediator(tabLayout, viewpager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    abstract fun getSelectionAdapter(): FragmentStateAdapter
}