package bj.drunkmessengerblocker

import android.app.Application

/**
 * Created by Josh Laird on 23/05/2017.
 */
class App : Application() {
    companion object {
        lateinit var component: AppComponent
        fun component(): AppComponent {
            return component
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupGraph()
    }

    private fun setupGraph() {
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
    }
}