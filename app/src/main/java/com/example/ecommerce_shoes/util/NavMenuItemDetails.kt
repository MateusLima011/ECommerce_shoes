package com.example.ecommerce_shoes.util

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import com.example.ecommerce_shoes.domain.NavMenuItem

class NavMenuItemDetails(
    var item: NavMenuItem? = null,
    var adapterPosition: Int = -1
) : ItemDetailsLookup.ItemDetails<Long>() {

    override fun getPosition() = adapterPosition

    override fun getSelectionKey() = item!!.id

    override fun inSelectionHotspot(e: MotionEvent) = true
}