package com.binish.todoapp.repositories

import com.binish.todoapp.models.Checklist
import com.binish.todoapp.models.Reminder
import com.binish.todoapp.utils.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmResults

class ToDoRepository(private val realm: Realm) {

    fun fetchAllCheckLists(): SingleLiveEvent<ToDo> {
        val singleLiveEvent = SingleLiveEvent<ToDo>()
        singleLiveEvent.value = ToDo(isSuccess = false)
        realm.beginTransaction()
        val realmResults = realm.where(Checklist::class.java).findAll()
        realm.commitTransaction()
        singleLiveEvent.value = ToDo(isSuccess = true, checklists = realmResults)
        return singleLiveEvent
    }

    fun fetchAllReminders(): SingleLiveEvent<ToDo>{
        val singleLiveEvent = SingleLiveEvent<ToDo>()
        singleLiveEvent.value = ToDo(isSuccess = false)
        realm.beginTransaction()
        val realmResults = realm.where(Reminder::class.java).findAll()
        realm.commitTransaction()
        singleLiveEvent.value = ToDo(isSuccess = true, reminder = realmResults)
        return singleLiveEvent
    }

    fun addCheckList(checkList: Checklist): SingleLiveEvent<ToDo>{
        val singleLiveEvent = SingleLiveEvent<ToDo>()
        singleLiveEvent.value = ToDo(isSuccess = false)
        realm.beginTransaction()
        checkList.id = getPrimaryKey(true)
        realm.insertOrUpdate(checkList)
        realm.commitTransaction()
        singleLiveEvent.value = ToDo(isSuccess = true)
        return singleLiveEvent
    }

    fun addReminder(reminder: Reminder): SingleLiveEvent<ToDo>{
        val singleLiveEvent = SingleLiveEvent<ToDo>()
        singleLiveEvent.value = ToDo(isSuccess = false)
        realm.beginTransaction()
        reminder.id = getPrimaryKey(false)
        realm.insertOrUpdate(reminder)
        realm.commitTransaction()
        singleLiveEvent.value = ToDo(isSuccess = true)
        return singleLiveEvent
    }

    fun updateCheckList(checkList: Checklist): SingleLiveEvent<ToDo>{
        val singleLiveEvent = SingleLiveEvent<ToDo>()
        singleLiveEvent.value = ToDo(isSuccess = false)
        realm.beginTransaction()
        realm.insertOrUpdate(checkList)
        realm.commitTransaction()
        singleLiveEvent.value = ToDo(isSuccess = true)
        return singleLiveEvent
    }

    fun updateReminder(reminder: Reminder): SingleLiveEvent<ToDo>{
        val singleLiveEvent = SingleLiveEvent<ToDo>()
        singleLiveEvent.value = ToDo(isSuccess = false)
        realm.beginTransaction()
        realm.insertOrUpdate(reminder)
        realm.commitTransaction()
        singleLiveEvent.value = ToDo(isSuccess = true)
        return singleLiveEvent
    }

    private fun getPrimaryKey(isCheckList: Boolean): Int{
        val num = realm.where(if(isCheckList) Checklist::class.java else Reminder::class.java).max("id")
        return if(num == null || num == 0) 1 else num.toInt() + 1
    }

    data class ToDo(
        val isSuccess: Boolean = true,
        val isError:Boolean = false,
        val checklists: RealmResults<Checklist>? =null,
        val reminder: RealmResults<Reminder>? =null
    )
}