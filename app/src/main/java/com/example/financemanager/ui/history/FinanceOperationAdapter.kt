package com.example.financemanager.ui.history

import android.app.Dialog
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financemanager.R
import com.example.financemanager.database.financeOperationDatabase.FinanceOperation
import kotlinx.android.synthetic.main.fragment_edit_category_name.*
import kotlinx.android.synthetic.main.fragment_info_message.*


class FinanceOperationAdapter(context: Context) : RecyclerView.Adapter<FinanceOperationViewHolder>() {

    private var context = context

    var data = listOf<FinanceOperation>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FinanceOperationViewHolder, position: Int) {
        val item = data[position]
        holder.typeOperation.text = item.typeOperation
        holder.categoryOperation.text = item.categoryOperation
        holder.categoryOperation.movementMethod = ScrollingMovementMethod()
        var amountString: String = item.amount.toString() + " ₽"
        holder.amountOperation.text = amountString
        holder.dateOperation.text = item.dateOperation
        holder.buttonInfoMessage.setOnClickListener(){
            val dialog: Dialog = Dialog(context)
            dialog.setContentView(R.layout.fragment_info_message)
            dialog.text_info_message.text = item.informationMessage
            dialog.text_info_message.movementMethod = ScrollingMovementMethod()
            dialog.show()
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
    val typeOperation: TextView = itemView.findViewById(R.id.type_operation)
    val categoryOperation: TextView = itemView.findViewById(R.id.category_operation)
    val amountOperation: TextView = itemView.findViewById(R.id.text_amount)
    val dateOperation: TextView = itemView.findViewById(R.id.date_operation)
    val buttonInfoMessage: Button = itemView.findViewById(R.id.button_info_message)
}