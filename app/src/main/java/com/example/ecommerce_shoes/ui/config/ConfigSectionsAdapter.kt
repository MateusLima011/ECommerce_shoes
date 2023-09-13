package com.example.ecommerce_shoes.ui.config

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ConfigSectionsAdapter(
    val context: Context, fragManager: FragmentManager, lifecycle: Lifecycle,
    private  val fragmentsList: List<ConfigFormFragment>,
    private val titleList: List<String>
) : FragmentStateAdapter(fragManager, lifecycle) {
    companion object {
        const val TOTAL_PAGES = 2
    }

    override fun getItemCount() = TOTAL_PAGES

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
        }
    }
