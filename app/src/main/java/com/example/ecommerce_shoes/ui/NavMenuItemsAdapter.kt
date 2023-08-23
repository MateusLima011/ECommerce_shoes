package com.example.ecommerce_shoes.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.domain.NavMenuItem
import com.example.ecommerce_shoes.util.NavMenuItemDetails

class NavMenuItemsAdapter(private val items: List<NavMenuItem>) :
    RecyclerView.Adapter<NavMenuItemsAdapter.ViewHolder>() {

    lateinit var selectionTracker: SelectionTracker<Long>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.nav_menu_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        private val tvLabel: TextView = itemView.findViewById(R.id.tv_label)
        val itemDetails = NavMenuItemDetails()

        init {
            ivIcon
            tvLabel
            itemDetails
        }

        fun bind(item: NavMenuItem) {
            tvLabel.text = item.label

            if (item.iconId != NavMenuItem.DEFAULT_ICON_ID) {
                ivIcon.setImageResource(item.iconId)
                ivIcon.visibility = View.VISIBLE
            } else {
                ivIcon.visibility = View.GONE
            }


            itemDetails.item = item
            itemDetails.adapterPosition = adapterPosition

            if (selectionTracker.isSelected(itemDetails.selectionKey)) {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context, R.color.colorNavItemSelected
                    )
                )
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }
        }


    }
}
