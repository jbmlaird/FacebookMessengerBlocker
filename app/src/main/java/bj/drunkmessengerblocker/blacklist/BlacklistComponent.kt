package bj.drunkmessengerblocker.blacklist

import bj.drunkmessengerblocker.ActivityScope
import bj.drunkmessengerblocker.AppComponent
import dagger.Component

/**
 * Created by Josh Laird on 23/05/2017.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(BlacklistModule::class))
interface BlacklistComponent {
    fun inject(activity: BlacklistActivity)
}