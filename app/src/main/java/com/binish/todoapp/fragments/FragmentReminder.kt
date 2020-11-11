package com.binish.todoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.binish.todoapp.R
import com.binish.todoapp.models.Reminder
import com.binish.todoapp.viewmodels.ToDoViewModel
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_reminder.*

class FragmentReminder: Fragment() {
    var toDoViewModel : ToDoViewModel? = null
    private var listener: FragmentReminderInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoViewModel = ToDoViewModel(Realm.getDefaultInstance())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentReminderInteraction)
            listener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageButtonDateReminder.setOnClickListener {
            showTimeDateDialog(false)
        }

        imageButtonTimeReminder.setOnClickListener {
            showTimeDateDialog(true)
        }

        imageButtonDoneReminder.setOnClickListener {
            toDoViewModel?.addReminder(Reminder().apply {
                title = editTextTitleReminder.text.toString()
                description = editTextDescriptionReminder.text.toString()
                time = textViewTimeReminder.text.toString()
                date = textViewDateReminder.text.toString()
            })?.observe(this, {
                if(it.isSuccess) {
                    Toast.makeText(requireContext(), "Reminder Created", Toast.LENGTH_SHORT).show()
                    listener?.onCreated()
                }
            })
        }
    }

    private fun showTimeDateDialog(isTime: Boolean){
        TimeDateDialogFragment.newInstance(isTime
        ) {
            if(isTime)
                textViewTimeReminder.text = it
            else
                textViewDateReminder.text = it
        }.show(fragmentManager!!, "TimeDateDialog")
    }

    interface FragmentReminderInteraction{
        fun onCreated()
    }
}