package com.example.ecommerce_shoes.util

import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_shoes.domain.NavMenuItem

class NavMenuItemKeyProvider(private val items: List<NavMenuItem>) :
    ItemKeyProvider<Long>(ItemKeyProvider.SCOPE_MAPPED) {

    override fun getKey(position: Int): Long = items[position].id

    override fun getPosition(key: Long): Int{
        val item = items.find { it.id == key }
        return if (item != null) items.indexOf(item) else RecyclerView.NO_POSITION
    }
}