package bj.drunkmessengerblocker.main

import dagger.Module
import dagger.Provides

/**
 * Created by Josh Laird on 23/05/2017.
 */
@Module
class MainModule(val view: MainContract.View) {
    @Provides
    fun provideMainContractView(): MainContract.View {
        return view
    }
}