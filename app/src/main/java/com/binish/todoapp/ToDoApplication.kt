package com.binish.todoapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class ToDoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("default.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()


        val configBuild = config.build()
        Realm.setDefaultConfiguration(configBuild)
    }
}