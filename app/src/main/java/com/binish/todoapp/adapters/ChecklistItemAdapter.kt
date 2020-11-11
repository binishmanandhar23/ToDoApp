package com.binish.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.binish.todoapp.R
import com.binish.todoapp.models.ChecklistItems
import io.realm.RealmList

class ChecklistItemAdapter(private val context: Context, private val checkListItems: RealmList<ChecklistItems>?, val listener: CheckItemInteraction): RecyclerView.Adapter<ChecklistItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_checkeditems, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val checkList = checkListItems!![position]
        holder.checkListItemName.text = checkList?.itemName
        holder.checkBoxCheckListItem.isChecked = checkList?.isChecked ?: false

        holder.checkBoxCheckListItem.setOnCheckedChangeListener { buttonView, isChecked ->
            checkList?.isChecked = isChecked
            listener.onChecked(checkList, position)
        }
    }

    override fun getItemCount(): Int = checkListItems?.size ?: 0

    fun interface CheckItemInteraction{
        fun onChecked(checkListItem: ChecklistItems?, position: Int)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val checkBoxCheckListItem = itemView.findViewById<CheckBox>(R.id.checkBoxCheckListItem)!!
        val checkListItemName = itemView.findViewById<TextView>(R.id.checkListItemName)!!
    }
}