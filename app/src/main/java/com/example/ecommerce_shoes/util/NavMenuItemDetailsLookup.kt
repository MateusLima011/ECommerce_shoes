package com.example.ecommerce_shoes.util

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_shoes.ui.NavMenuItemsAdapter

class NavMenuItemDetailsLookup(private val rvMenuItems: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = rvMenuItems.findChildViewUnder(event.x, event.y)

      return view?.let {
          val holder = rvMenuItems.getChildViewHolder(it) as? NavMenuItemsAdapter.ViewHolder
          holder?.getItemDetails()
      }
    }
}