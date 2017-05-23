package bj.drunkmessengerblocker.blacklist

import bj.drunkmessengerblocker.ActivityScope
import bj.drunkmessengerblocker.utils.SharedPrefsManager
import dagger.Module
import dagger.Provides

/**
 * Created by Josh Laird on 23/05/2017.
 */
@Module
class BlacklistModule(val view: BlacklistContract.View) {
    @Provides
    fun provideBlacklistView(): BlacklistContract.View {
        return view
    }

    @Provides
    @ActivityScope
    fun provideBlacklistPresenter(view: BlacklistContract.View, sharedPrefsManager: SharedPrefsManager): BlacklistPresenter {
        return BlacklistPresenter(view, sharedPrefsManager)
    }

    @Provides
    @ActivityScope
    fun provideBlacklistController(presenter: BlacklistPresenter, sharedPrefsManager: SharedPrefsManager): BlacklistController {
        return BlacklistController(presenter, sharedPrefsManager)
    }
}