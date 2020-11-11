package com.binish.todoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.binish.todoapp.models.Checklist
import com.binish.todoapp.models.ChecklistItems
import com.binish.todoapp.models.Reminder
import com.binish.todoapp.viewmodels.ToDoViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToDoRepoTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var realm: Realm
    lateinit var toDoViewModel: ToDoViewModel


    @Before
    fun onSetup() {
    }

    @Test
    fun testFetchAllCheckList() {
        val activityScenarioRule: ActivityScenario<MainActivity> =
            ActivityScenario.launch(MainActivity::class.java)
        activityScenarioRule.onActivity { _ ->
            realm = Realm.getDefaultInstance()
            addData(true)
            toDoViewModel = ToDoViewModel(realm)
            toDoViewModel.fetchAllCheckLists()?.observeForever {
                if (it.isSuccess)
                    Assert.assertEquals("Checklist size should be 2", 2, it.checklists?.size)
            }
            realm.close()
        }
    }

    @Test
    fun testFetchAllReminder() {
        val activityScenarioRule: ActivityScenario<MainActivity> =
            ActivityScenario.launch(MainActivity::class.java)
        activityScenarioRule.onActivity { _ ->
            realm = Realm.getDefaultInstance()
            addData(false)
            toDoViewModel = ToDoViewModel(realm)
            toDoViewModel.fetchAllReminders()?.observeForever {
                if (it.isSuccess)
                    Assert.assertEquals("Reminders size should be 2", 2, it.reminder?.size)
            }
            realm.close()
        }
    }

    private fun addData(isChecklist: Boolean) {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
        if(isChecklist) {
            val checklistItems = RealmList<ChecklistItems>()
            checklistItems.add(ChecklistItems().apply {
                id = 1
                itemName = "SubItem 1"
                isChecked = true
            })

            checklistItems.add(ChecklistItems().apply {
                id = 2
                itemName = "SubItem 2"
                isChecked = false
            })
            realm.beginTransaction()
            realm.insertOrUpdate(Checklist().apply {
                id = 1
                title = "Test 1"
                this.checklistItems = checklistItems
            })
            realm.insertOrUpdate(Checklist().apply {
                id = 2
                title = "Test 2"
                this.checklistItems = checklistItems
            })
            realm.commitTransaction()
        } else{
            realm.beginTransaction()
            realm.insertOrUpdate(Reminder().apply {
                id = 1
                title = "Test 1"
                description = "Testing"
            })
            realm.insertOrUpdate(Reminder().apply {
                id = 2
                title = "Test 2"
                description = "Testing"
            })
            realm.commitTransaction()
        }
    }
}