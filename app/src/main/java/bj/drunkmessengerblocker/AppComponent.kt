package bj.drunkmessengerblocker

import android.content.Context
import bj.drunkmessengerblocker.utils.SharedPrefsManager
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Josh Laird on 23/05/2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: App)

    fun getContext(): Context

    fun getSharedPrefs(): SharedPrefsManager
}