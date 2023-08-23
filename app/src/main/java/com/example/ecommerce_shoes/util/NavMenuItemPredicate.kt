package com.example.ecommerce_shoes.util

import androidx.recyclerview.selection.SelectionTracker
import com.example.ecommerce_shoes.MainActivity
import com.example.ecommerce_shoes.data.NavMenuItemsDataBase

class NavMenuItemPredicate(private val activity: MainActivity) :
    SelectionTracker.SelectionPredicate<Long>() {

    override fun canSelectMultiple() = false

    override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {

        if (!nextState) {
            val lastItemId = NavMenuItemsDataBase(activity).getLastItemId()
            val firstItemLoggedId = NavMenuItemsDataBase(activity).getFirstItemLoggedId()

            val sizeNavMenuItems = activity.selectNavMenuItems.selection.size()
            val sizeNavMenuItemsLogged = activity.selectNavMenuItemsLogged.selection.size()

            if ((key <= lastItemId && sizeNavMenuItemsLogged == 0) ||
                (key >= firstItemLoggedId && sizeNavMenuItems == 0))
            {
                return false
            }
        }
        return true
    }

    override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean = true
}