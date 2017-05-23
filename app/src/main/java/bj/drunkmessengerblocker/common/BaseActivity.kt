package bj.drunkmessengerblocker.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bj.drunkmessengerblocker.App
import bj.drunkmessengerblocker.AppComponent

/**
 * Created by Josh Laird on 23/05/2017.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComponent(App.component())
    }

    abstract fun setupComponent(appComponent: AppComponent)
}