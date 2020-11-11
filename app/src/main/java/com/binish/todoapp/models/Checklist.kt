package com.binish.todoapp.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Checklist: RealmObject() {
    @PrimaryKey
    var id: Int = 0

    var title: String? = null
    var checklistItems: RealmList<ChecklistItems>? = null

    var isChecked: Boolean = false

    var time: String? = null
    var date: String? = null
}