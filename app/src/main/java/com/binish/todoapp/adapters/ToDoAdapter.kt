package com.binish.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.binish.todoapp.R
import com.binish.todoapp.models.Checklist
import com.binish.todoapp.models.Reminder
import com.binish.todoapp.utils.Enums
import io.realm.RealmResults

class ToDoAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context? = null
    private var checkedItemsRealm: RealmResults<Checklist>? = null
    private var remindersRealm: RealmResults<Reminder>? = null
    private var type: Enums.ViewType? = null
    private var listener: ToDoInteraction? = null

    constructor(context: Context,
                checkedItemsRealm: RealmResults<Checklist>,
                type: Enums.ViewType,
                listener: ToDoInteraction, ){
        this.context = context
        this.checkedItemsRealm = checkedItemsRealm
        this.type = type
        this.listener = listener
    }

    constructor(context: Context,
                type: Enums.ViewType,
                remindersRealm: RealmResults<Reminder>,
                ){
        this.context = context
        this.remindersRealm = remindersRealm
        this.type = type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (type == Enums.ViewType.CHECKLIST)
            CheckListViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_todo_checklist, parent, false)
            )
        else
            ReminderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_todo_reminder, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(type == Enums.ViewType.CHECKLIST){
            val checkListHolder = holder as CheckListViewHolder
            val checklist = checkedItemsRealm?.get(position)
            checkListHolder.textViewChecklistLabel.visibility = if(position == 0) View.VISIBLE else View.GONE
            checkListHolder.textViewChecklistHeading.text = checklist?.title
            checkListHolder.textViewChecklistDate.text = checklist?.date
            checkListHolder.textViewChecklistTime.text = checklist?.time

            checkListHolder.checkboxCheckList.isChecked = checklist?.isChecked ?: false
            checkListHolder.checkboxCheckList.setOnCheckedChangeListener { _, isChecked ->
                listener?.onCheckBoxChecked(checklist, isChecked)
            }
        } else {
            val reminderListHolder = holder as ReminderViewHolder
            val reminder = remindersRealm?.get(position)
            reminderListHolder.textViewReminderLabel.visibility = if(position == 0) View.VISIBLE else View.GONE
            reminderListHolder.textViewReminderHeading.text = reminder?.title
            reminderListHolder.textViewReminderDate.text = reminder?.date
            reminderListHolder.textViewReminderTime.text = reminder?.time
        }
    }

    override fun getItemCount(): Int = if(type == Enums.ViewType.CHECKLIST) checkedItemsRealm?.size ?: 0 else remindersRealm?.size ?: 0

    fun interface ToDoInteraction{
        fun onCheckBoxChecked(checklist: Checklist?, isChecked: Boolean)
    }

    inner class CheckListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewChecklistLabel = itemView.findViewById<TextView>(R.id.textViewChecklistLabel)!!
        val textViewChecklistHeading = itemView.findViewById<TextView>(R.id.textViewChecklistHeading)!!
        val textViewChecklistDate = itemView.findViewById<TextView>(R.id.textViewChecklistDate)!!
        val textViewChecklistTime = itemView.findViewById<TextView>(R.id.textViewChecklistTime)!!
        val checkboxCheckList = itemView.findViewById<CheckBox>(R.id.checkboxCheckList)!!
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewReminderLabel = itemView.findViewById<TextView>(R.id.textViewReminderLabel)!!
        val textViewReminderHeading = itemView.findViewById<TextView>(R.id.textViewReminderHeading)!!
        val textViewReminderDate = itemView.findViewById<TextView>(R.id.textViewReminderDate)!!
        val textViewReminderTime = itemView.findViewById<TextView>(R.id.textViewReminderTime)!!
    }
}