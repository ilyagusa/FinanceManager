package com.example.financemanager.ui.home.expensesCategory

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import kotlinx.android.synthetic.main.fragment_choose_delete_dialog.*
import kotlinx.android.synthetic.main.fragment_edit_category_name.*


class ExpensesCategoryAdapter(viewModel: ExpensesCategoryViewModel, context: Context) : RecyclerView.Adapter<ExpensesCategoryViewHolder>() {

    private var viewModelExpenses : ExpensesCategoryViewModel = viewModel
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
        holder.categoryName.text = item.categoryName
        holder.categoryName.movementMethod = ScrollingMovementMethod()

        //Удаление категории
        holder.deleteButton.setOnClickListener(){
            createDeleteChooseDialog(item)
        }

        //Редактирование названия категории
        holder.redactionButton.setOnClickListener(){
            createRedactCategoryDialog(item)
        }

        //Добавление расходов
        holder.expenseAmountButton.setOnClickListener() {
            val dialog: Dialog = Dialog(context)
            dialog.setContentView(R.layout.fragment_create_expense)


            viewModelExpenses.insertFinanceOperation((-1)*1000.0,res.getString(R.string.expenses),item.categoryName,"myMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMe" +
                    "ssagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessa" +
                    "gemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessagemyMessage")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.list_button_expenses, parent, false)
        return ExpensesCategoryViewHolder(view)
    }


    private fun createRedactCategoryDialog(item: ExpensesCategory) {
        val dialog: Dialog = Dialog(context)
        dialog.setContentView(R.layout.fragment_edit_category_name)
        dialog.text_category_change.movementMethod = ScrollingMovementMethod()
        val str: String = context.resources.getString(R.string.text_change_name_category) + " '" + item.categoryName + "'"
        dialog.text_category_change.text = str
        val oldCatgoryName = item.categoryName
        dialog.button_submit_name_category.setOnClickListener(){
            viewModelExpenses.updateCategoryName(oldCatgoryName, dialog.edit_name_category.text.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun createDeleteChooseDialog(item: ExpensesCategory) {
        val dialog: Dialog = Dialog(context)
        dialog.setContentView(R.layout.fragment_choose_delete_dialog)
        dialog.textViewChooseDelete.movementMethod = ScrollingMovementMethod()
        val str: String = context.resources.getString(R.string.choose_delete_dialog) + " '" + item.categoryName + "' ?"
        dialog.textViewChooseDelete.text = str
        //closed dialog
        dialog.cancel_button.setOnClickListener(){
            dialog.dismiss()
        }
        //submit delete expenses_category
        dialog.submit_button.setOnClickListener(){
            viewModelExpenses.deleteCategoryByName(item.categoryName)
            dialog.dismiss()
            Toast.makeText(context,"Категори : " + item.categoryName + " удалена", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }


}


class ExpensesCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val categoryName: TextView = itemView.findViewById(R.id.text_expenses_list)
    val expenseAmountButton: Button = itemView.findViewById(R.id.button_expense_amount)
    val redactionButton: Button = itemView.findViewById(R.id.button_redaction)
    val deleteButton: Button = itemView.findViewById(R.id.button_delete)
}