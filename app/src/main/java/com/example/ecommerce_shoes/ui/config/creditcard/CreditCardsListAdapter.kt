package com.example.ecommerce_shoes.ui.config.creditcard

import android.app.Activity
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ColorUtils
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.domain.CreditCard
import com.example.ecommerce_shoes.util.isValidPassword
import com.example.ecommerce_shoes.util.validate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Handler

class CreditCardsListAdapter(
    private val fragment: CreditCardsListFragment,
    private val items: MutableList<CreditCard>,
    private val continueAction: (Boolean) -> Unit
) : RecyclerView.Adapter<CreditCardsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreditCardsListAdapter.ViewHolder {

        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.credit_card_item, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(
        holder: CreditCardsListAdapter.ViewHolder,
        position: Int
    ) {
        holder.setData(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val tvEnterprise: TextView
        private val tvNumber: TextView
        private val tvOwnerName: TextView
        private val btRemove: Button
        private val activity: Activity = itemView.context as Activity
        private lateinit var alertDialog: AlertDialog

        init {
            tvEnterprise = itemView.findViewById(R.id.tvEnterprise)
            tvNumber = itemView.findViewById(R.id.tvNumber)
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName)
            btRemove = itemView.findViewById(R.id.btRemove)
            btRemove.setOnClickListener(this)
        }

        fun setData(item: CreditCard) {
            tvEnterprise.text = item.enterprise
            tvNumber.text = item.getNumberAsHidden()
            tvOwnerName.text = item.getOwnerFullNameAsHidden()
        }

        override fun onClick(v: View?) {

            val selectedItem = adapterPosition

            if (selectedItem != RecyclerView.NO_POSITION) {
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Confirmar Remoção")
                alertDialogBuilder.setMessage("Deseja realmente remover este cartão de crédito?")
                alertDialogBuilder.setPositiveButton("Sim") { _, _ ->

                    val dialogView =
                        activity.layoutInflater.inflate(R.layout.dialog_password, null)

                    val builder = AlertDialog.Builder(activity)
                        .setView(dialogView)
                        .setPositiveButton(R.string.dialog_password_go) { _, _ ->
                            continueAction(true)
                            btRemove.isEnabled = false
                            btRemove.text = "Removendo..."
                            android.os.Handler(Looper.getMainLooper()).postDelayed({
                                items.removeAt(selectedItem)
                                notifyItemRemoved(selectedItem)
                                continueAction(false)
                            }, 2000)
                        }
                        .setNegativeButton(R.string.dialog_password_cancel) { dialog, _ -> dialog.cancel() }
                        .setCancelable(false)

                    val etPassword = dialogView.findViewById<EditText>(R.id.etPassword)
                    val positiveButton =
                        builder.create().getButton(AlertDialog.BUTTON_POSITIVE)
                    val negativeButton =
                        builder.create().getButton(AlertDialog.BUTTON_NEGATIVE)

                    etPassword?.validate(
                        { it.isValidPassword() },
                        activity.getString(R.string.invalid_password)
                    )

                    etPassword?.setOnEditorActionListener { _, _, _ ->
                        alertDialog.cancel()
                        false
                    }

                    positiveButton?.setTextColor(ColorUtils.getColor(R.color.colorText))
                    negativeButton?.setTextColor(ColorUtils.getColor(R.color.colorText))

                    builder.show()
                }

                alertDialogBuilder.setNegativeButton("Não") { dialog, _ ->
                    dialog.cancel()
                    dialog.dismiss()
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }


}
