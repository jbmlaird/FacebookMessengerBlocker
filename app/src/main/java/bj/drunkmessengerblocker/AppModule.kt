package bj.drunkmessengerblocker

import android.content.Context
import bj.drunkmessengerblocker.utils.SharedPrefsManager
import dagger.Module
import dagger.Provides

/**
 * Created by Josh Laird on 23/05/2017.
 */
@Module
class AppModule() {
    lateinit var context: Context
    lateinit var sharedPrefsManager: SharedPrefsManager

    constructor(context: Context) : this() {
        this.context = context
        sharedPrefsManager = SharedPrefsManager(context)
    }

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideSharedPrefsManager(): SharedPrefsManager {
        return sharedPrefsManager
    }
}