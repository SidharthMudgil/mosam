package com.sidharth.mosam

import android.app.Application
import com.sidharth.mosam.di.component.AppComponent
import com.sidharth.mosam.di.component.DaggerAppComponent
import com.sidharth.mosam.di.module.DatabaseModule

class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeAppComponent()
    }

    private fun initializeAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(instance))
            .build()
    }
}