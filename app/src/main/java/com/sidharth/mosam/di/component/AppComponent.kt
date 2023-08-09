package com.sidharth.mosam.di.component

import com.sidharth.mosam.di.module.AppModule
import com.sidharth.mosam.di.module.DatabaseModule
import com.sidharth.mosam.di.module.NetworkModule
import com.sidharth.mosam.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}