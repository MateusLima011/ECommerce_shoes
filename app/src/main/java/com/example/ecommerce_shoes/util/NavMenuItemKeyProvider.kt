package com.example.ecommerce_shoes.util

import androidx.recyclerview.selection.ItemKeyProvider
import com.example.ecommerce_shoes.domain.NavMenuItem

class NavMenuItemKeyProvider(private val items: List<NavMenuItem>) :
    ItemKeyProvider<Long>(SCOPE_MAPPED) {

    override fun getKey(position: Int): Long = items[position].id

    override fun getPosition(key: Long) = items.indexOf(items.single { item ->
        item.id == key
    })

}