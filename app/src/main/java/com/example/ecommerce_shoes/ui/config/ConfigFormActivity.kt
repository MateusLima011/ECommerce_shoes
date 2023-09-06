package com.example.ecommerce_shoes.ui.config

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.ActivityConfigConnectionDataBinding
import com.example.ecommerce_shoes.databinding.FragmentConfigPasswordBinding
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate
import com.google.android.material.tabs.TabLayoutMediator

abstract class ConfigFormActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityConfigConnectionDataBinding.inflate(layoutInflater).apply { setContentView(root) }
    }
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
            when (position) {
                ConfigSectionsAdapter.FIRST_PAGE_POS -> tab.text = "EMAIL"
                ConfigSectionsAdapter.SECOND_PAGE_POS -> tab.text = "SENHA"
            }
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