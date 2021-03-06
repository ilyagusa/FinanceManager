package com.example.financemanager.ui.history

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.financemanager.MainActivity
import com.example.financemanager.R
import com.example.financemanager.database.financeOperationDatabase.FinanceOperation
import com.example.financemanager.ui.dialog.DeleteFinanceOperationDialog
import com.example.financemanager.ui.dialog.InfoMessageDialog
import kotlinx.android.synthetic.main.fragment_edit_category_name.*
import kotlinx.android.synthetic.main.fragment_info_message.*


class FinanceOperationAdapter(context: Context) : RecyclerView.Adapter<FinanceOperationViewHolder>() {

    private val res: Resources = context.resources

    var data = listOf<FinanceOperation>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FinanceOperationViewHolder, position: Int) {
        val item = data[position]
        val activity = holder.activity
        if (item.typeOperation == "Расходы") {
            holder.typeOperation.text = res.getString(R.string.expenses)
        } else {
            holder.typeOperation.text = res.getString(R.string.income)
        }
        holder.itemView.setOnLongClickListener() {
            val args: Bundle = Bundle()
            args.putLong("id", item.operationId)
            val dialog = DeleteFinanceOperationDialog()
            dialog.arguments = args
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "deleteFinanceOprationDialog") }
            return@setOnLongClickListener true
        }
        holder.categoryOperation.text = item.categoryOperation
        holder.categoryOperation.movementMethod = ScrollingMovementMethod()
        var amountString: String = item.amount.toString() + " ₽"
        holder.amountOperation.text = amountString
        holder.dateOperation.text = item.dateOperation
        holder.buttonInfoMessage.setOnClickListener() {
            val args: Bundle = Bundle()
            args.putString("info_message", item.informationMessage)
            val dialog = InfoMessageDialog()
            dialog.arguments = args
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "infoMessageDialog") }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceOperationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.fragment_finance_operation, parent, false)
        return FinanceOperationViewHolder(view)
    }

}

class FinanceOperationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val activity = itemView.context as? MainActivity
    val context: Context = itemView.context
    val typeOperation: TextView = itemView.findViewById(R.id.type_operation)
    val categoryOperation: TextView = itemView.findViewById(R.id.category_operation)
    val amountOperation: TextView = itemView.findViewById(R.id.text_amount)
    val dateOperation: TextView = itemView.findViewById(R.id.date_operation)
    val buttonInfoMessage: Button = itemView.findViewById(R.id.button_info_message)
}