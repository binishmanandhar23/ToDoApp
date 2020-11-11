package com.binish.todoapp.viewmodels

import androidx.lifecycle.ViewModel
import com.binish.todoapp.models.Checklist
import com.binish.todoapp.models.Reminder
import com.binish.todoapp.repositories.ToDoRepository
import com.binish.todoapp.utils.SingleLiveEvent
import io.realm.Realm

class ToDoViewModel(private val realm: Realm): ViewModel() {
    private var toDoRepository: ToDoRepository? = null
    init {
        toDoRepository = ToDoRepository(realm)
    }

    fun fetchAllCheckLists(): SingleLiveEvent<ToDoRepository.ToDo>? {
        return toDoRepository?.fetchAllCheckLists()
    }

    fun fetchAllReminders(): SingleLiveEvent<ToDoRepository.ToDo>? {
        return toDoRepository?.fetchAllReminders()
    }

    fun addCheckList(checklist: Checklist): SingleLiveEvent<ToDoRepository.ToDo>? {
        return toDoRepository?.addCheckList(checklist)
    }

    fun addReminder(reminder: Reminder): SingleLiveEvent<ToDoRepository.ToDo>? {
        return toDoRepository?.addReminder(reminder)
    }

    fun updateCheckList(checkList: Checklist): SingleLiveEvent<ToDoRepository.ToDo>?{
        return toDoRepository?.updateCheckList(checkList)
    }

    fun updateReminder(reminder: Reminder): SingleLiveEvent<ToDoRepository.ToDo>? {
        return toDoRepository?.updateReminder(reminder)
    }
}