package com.example.ecommerce_shoes.ui.config.deliveryaddress

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.domain.DeliveryAddress
import com.example.ecommerce_shoes.ui.config.ConfigListFragment

class DeliveryAddressesListAdapter(
    private val fragment: ConfigListFragment,
    private val items: MutableList<DeliveryAddress>
) :
    RecyclerView.Adapter<DeliveryAddressesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryAddressesListAdapter.ViewHolder {

        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.delivery_address_item, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(
        holder: DeliveryAddressesListAdapter.ViewHolder,
        position: Int
    ) {
        holder.setData(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val tvStreet: TextView
        private val tvNumber: TextView
        private val tvZipCode: TextView
        private val tvNeighborhood: TextView
        private val tvCity: TextView
        private val tvState: TextView
        private val tvComplement: TextView
        private val btUpdate: Button
        private val btRemove: Button

        init {
            tvStreet = itemView.findViewById(R.id.tvStreet)
            tvNumber = itemView.findViewById(R.id.tvNumber)
            tvZipCode = itemView.findViewById(R.id.tvZipCode)
            tvNeighborhood = itemView.findViewById(R.id.tvNeighborhood)
            tvCity = itemView.findViewById(R.id.tvCity)
            tvState = itemView.findViewById(R.id.tvState)
            tvComplement = itemView.findViewById(R.id.tvComplement)

            btUpdate = itemView.findViewById(R.id.btUpdateDeliveryAddress)
            btUpdate.setOnClickListener(this)

            btRemove = itemView.findViewById(R.id.btRemoveDeliveryAddress)
            btRemove.setOnClickListener(this)
        }

        fun setData(item: DeliveryAddress) {

            tvStreet.text = item.street
            tvNumber.text = item.number.toString()
            tvZipCode.text = item.zipCode
            tvNeighborhood.text = item.neighborhood
            tvCity.text = item.city

            tvState.text = item.getStateName(fragment.requireContext())
            tvComplement.text = item.complement
        }

        private fun toUpdate(selectedItem: Int) {
            val updateFrag = UpdateDeliveryAddressFragment()

            val bundle = Bundle()
            bundle.putParcelable(
                DeliveryAddress.KEY,
                items[selectedItem]
            )
            updateFrag.arguments = bundle

            val transaction = fragment.activity?.supportFragmentManager?.beginTransaction()

            transaction
                ?.replace(
                    R.id.fl_root,
                    updateFrag
                )
                ?.addToBackStack(null)
                ?.commit()
        }


        override fun onClick(v: View) {
            val selectedItem = adapterPosition

            if (v.id == btRemove.id) {
                toRemove(selectedItem)
            } else {
                toUpdate(selectedItem)
            }

        }

        private fun toRemove(selectedItem: Int) {
            if (selectedItem != RecyclerView.NO_POSITION) {
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Confirmar Remoção")
                alertDialogBuilder.setMessage("Deseja realmente remover este endereço?")
                alertDialogBuilder.setPositiveButton("Sim") { _, _ ->
                    btRemove.isEnabled = false
                    btRemove.text = "Removendo"
                    Handler(Looper.getMainLooper()).postDelayed({
                        items.removeAt(selectedItem)
                        notifyItemRemoved(selectedItem)
                    }, 200)
                }
                    .setNegativeButton("Não") { dialog, _ ->
                        dialog.cancel()
                        dialog.dismiss()
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }
}
