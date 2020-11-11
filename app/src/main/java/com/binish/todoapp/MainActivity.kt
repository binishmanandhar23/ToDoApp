package com.binish.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.binish.todoapp.fragments.FragmentCheckList
import com.binish.todoapp.fragments.FragmentReminder
import com.binish.todoapp.fragments.FragmentToDoBody
import com.binish.todoapp.utils.AppUtils
import com.binish.todoapp.viewmodels.ToDoViewModel
import com.leinardi.android.speeddial.SpeedDialActionItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentCheckList.FragmentChecklistInteraction,
    FragmentReminder.FragmentReminderInteraction {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        AppUtils.changeStatusBarColor(this, true, R.color.White)
        loadBodyFragment()
        fabWork()
    }

    private fun fabWork() {
        fabMain.addActionItem(
            SpeedDialActionItem.Builder(R.id.fabCheckList, R.drawable.ic_todo)
                .setLabel("Add CheckList").create()
        )
        fabMain.addActionItem(
            SpeedDialActionItem.Builder(R.id.fabReminder, R.drawable.ic_reminder)
                .setLabel("Add Reminder").create()
        )

        fabMain.setOnActionSelectedListener {
            when (it.id) {
                R.id.fabCheckList -> {
                    toDoFragment(FragmentCheckList())
                }
                R.id.fabReminder -> {
                    toDoFragment(FragmentReminder())
                }
                else -> {
                }
            }
            fabMain.close()
            return@setOnActionSelectedListener true
        }
    }

    private fun loadBodyFragment() {
        val toDoViewModel = ToDoViewModel(Realm.getDefaultInstance())
        toDoViewModel.fetchAllCheckLists()?.observe(this, {
            if (it.checklists.isNullOrEmpty())
                toDoViewModel.fetchAllReminders()?.observe(this, { response ->
                    if (response.reminder.isNullOrEmpty())
                        textViewEmpty.visibility = View.VISIBLE
                    else
                        AppUtils.startFragment(
                            FragmentToDoBody(),
                            supportFragmentManager,
                            R.id.mainContainer,
                            false,
                            "MainStack"
                        )
                })
            else
                AppUtils.startFragment(
                    FragmentToDoBody(),
                    supportFragmentManager,
                    R.id.mainContainer,
                    false,
                    "MainStack"
                )

        })
    }

    private fun toDoFragment(fragment: Fragment) {
        AppUtils.startFragment(fragment, supportFragmentManager, R.id.mainContainer, true, "ToDo")
    }

    override fun onCreated() {
        supportFragmentManager.popBackStack()
        loadBodyFragment()
    }
}