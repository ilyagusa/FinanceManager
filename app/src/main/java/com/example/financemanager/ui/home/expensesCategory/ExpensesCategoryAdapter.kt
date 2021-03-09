package com.example.financemanager.ui.home.expensesCategory

import android.content.Context
import android.content.res.Resources
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financemanager.MainActivity
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.ui.dialog.CreateExpensesDialog
import com.example.financemanager.ui.dialog.DeleteCategoryDialog
import com.example.financemanager.ui.dialog.RedactCategoryDialog


class ExpensesCategoryAdapter(viewModel: FinanceOperationViewModel, context: Context) : RecyclerView.Adapter<ExpensesCategoryViewHolder>() {

    private var viewModelExpenses : FinanceOperationViewModel = viewModel
    private var context = context
    private val res: Resources = context.resources

    var data = listOf<ExpensesCategory>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ExpensesCategoryViewHolder, position: Int) {
        val item = data[position]
        val activity = holder.activity
        holder.categoryName.text = item.categoryName
        holder.categoryName.movementMethod = ScrollingMovementMethod()

        //Удаление категории
        holder.deleteButton.setOnClickListener(){
            //createDeleteChooseDialog(item)
            val dialog = DeleteCategoryDialog(item)
            activity?.supportFragmentManager?.let {it1 -> dialog.show(it1, "deleteCategoryDialog")}
        }

        //Редактирование названия категории
        holder.redactionButton.setOnClickListener(){
            //createRedactCategoryDialog(item)
            val dialog = RedactCategoryDialog(item)
            activity?.supportFragmentManager?.let {it1 -> dialog.show(it1, "redactCategoryDialog")}
        }

        //Добавление расходов
        holder.expenseAmountButton.setOnClickListener() {
            val dialog = CreateExpensesDialog(item)
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createExpensesDialog") }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.list_button_expenses, parent, false)
        return ExpensesCategoryViewHolder(view)
    }

}


class ExpensesCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val activity = itemView.context as? MainActivity
    val categoryName: TextView = itemView.findViewById(R.id.text_expenses_list)
    val expenseAmountButton: Button = itemView.findViewById(R.id.button_expense_amount)
    val redactionButton: Button = itemView.findViewById(R.id.button_redaction)
    val deleteButton: Button = itemView.findViewById(R.id.button_delete)
}