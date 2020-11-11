package com.binish.todoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binish.todoapp.R
import com.binish.todoapp.adapters.ChecklistItemAdapter
import com.binish.todoapp.models.Checklist
import com.binish.todoapp.models.ChecklistItems
import com.binish.todoapp.viewmodels.ToDoViewModel
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.fragment_checklist.*

class FragmentCheckList: Fragment() {
    private var toDoViewModel: ToDoViewModel? = null
    private var checkListItems: RealmList<ChecklistItems>? = null
    private var listener: FragmentChecklistInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoViewModel = ToDoViewModel(Realm.getDefaultInstance())
        checkListItems = RealmList<ChecklistItems>()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentChecklistInteraction)
            listener = context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checklist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageButtonDateCheckList.setOnClickListener {
            showTimeDateDialog(false)
        }
        imageButtonTimeCheckList.setOnClickListener {
            showTimeDateDialog(true)
        }
        textViewAddItemsChecklist.setOnClickListener {
            InputDialogFragment {
                checkListItems?.add(ChecklistItems().apply {
                    id = if(checkListItems.isNullOrEmpty()) 1 else (checkListItems!!.size + 1)
                    itemName = it
                    isChecked = false
                })
                populateRecyclerView()
            }.show(fragmentManager!!,"InputDialog")

        }

        imageButtonDoneCheckList.setOnClickListener {
            toDoViewModel?.addCheckList(Checklist().apply {
                title = editTextTitleCheckList.text.toString()
                this.checklistItems = checkListItems
                time = textViewTimeCheckList.text.toString()
                date = textViewDateCheckList.text.toString()
            })?.observe(this, {
                if(it.isSuccess){
                    Toast.makeText(requireContext(), "Checklist created", Toast.LENGTH_SHORT).show()
                    listener?.onCreated()
                }
            })
        }
    }

    private fun populateRecyclerView(){
        recyclerViewCheckListItems.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewCheckListItems.adapter = ChecklistItemAdapter(requireContext(), checkListItems
        ) { checkListItem, position ->
            checkListItems?.removeAt(position)
            checkListItems?.add(position, checkListItem)
            recyclerViewCheckListItems.adapter?.notifyDataSetChanged()
        }
    }
    private fun showTimeDateDialog(isTime: Boolean) {
        TimeDateDialogFragment.newInstance(
            isTime
        ) {
            if (isTime)
                textViewTimeCheckList.text = it
            else
                textViewDateCheckList.text = it
        }.show(fragmentManager!!, "TimeDateDialog")
    }

    interface FragmentChecklistInteraction{
        fun onCreated()
    }
}