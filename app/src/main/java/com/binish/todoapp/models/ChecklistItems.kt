package com.binish.todoapp.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChecklistItems: RealmObject() {
    @PrimaryKey
    var id: Int = 0

    var itemName: String? = null
    var isChecked: Boolean = false
}