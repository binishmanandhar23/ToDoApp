package com.binish.todoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.binish.todoapp.R
import com.binish.todoapp.adapters.ToDoAdapter
import com.binish.todoapp.models.Checklist
import com.binish.todoapp.utils.Enums
import com.binish.todoapp.viewmodels.ToDoViewModel
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_todo_body.*

class FragmentToDoBody: Fragment() {
    var todoViewModel: ToDoViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoViewModel = ToDoViewModel(Realm.getDefaultInstance())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_body, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getChecklists()
        getReminders()
    }

    private fun getChecklists(){
        todoViewModel?.fetchAllCheckLists()?.observe(this, {
            if(it.isSuccess && !it.checklists.isNullOrEmpty()) {
                recyclerViewToDoCheckList.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerViewToDoCheckList.adapter = ToDoAdapter(requireContext(),it.checklists, Enums.ViewType.CHECKLIST) { checkList, isChecked ->
                    if(checkList != null)
                        todoViewModel?.updateCheckList(Checklist().apply {
                            this.id = checkList.id
                            this.title = checkList.title
                            this.checklistItems = checkList.checklistItems
                            this.isChecked = isChecked
                            this.time = checkList.time
                            this.date = checkList.date
                        })?.observe(this, { response ->

                        })
                }
            }
        })
    }

    private fun getReminders(){
        todoViewModel?.fetchAllReminders()?.observe(this, {
            if(it.isSuccess && !it.reminder.isNullOrEmpty()) {
                recyclerViewToDoReminder.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerViewToDoReminder.adapter = ToDoAdapter(requireContext(), Enums.ViewType.REMINDER, it.reminder)
            }
        })
    }
}