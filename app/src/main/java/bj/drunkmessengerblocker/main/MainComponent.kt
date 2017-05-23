package bj.drunkmessengerblocker.main

import bj.drunkmessengerblocker.ActivityScope
import bj.drunkmessengerblocker.AppComponent
import dagger.Component

/**
 * Created by Josh Laird on 23/05/2017.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(activity: MainActivity)
}