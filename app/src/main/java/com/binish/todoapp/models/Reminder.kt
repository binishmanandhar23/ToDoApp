package com.binish.todoapp.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Reminder: RealmObject() {
    @PrimaryKey
    var id: Int = 0

    var title: String? = null
    var description: String? = null

    var time: String? = null
    var date: String? = null
}