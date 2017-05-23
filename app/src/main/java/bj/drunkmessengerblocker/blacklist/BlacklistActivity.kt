package bj.drunkmessengerblocker.blacklist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import bj.drunkmessengerblocker.AppComponent
import bj.drunkmessengerblocker.R
import bj.drunkmessengerblocker.common.BaseActivity
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_blacklist.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 23/05/2017.
 */
class BlacklistActivity : BaseActivity(), BlacklistContract.View {
    @Inject lateinit var presenter: BlacklistPresenter
    @Inject lateinit var controller: BlacklistController

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, BlacklistActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blacklist)
        ButterKnife.bind(this)

        setupRecyclerView()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupComponent(appComponent: AppComponent) {
        DaggerBlacklistComponent.builder()
                .appComponent(appComponent)
                .blacklistModule(BlacklistModule(this))
                .build()
                .inject(this)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    @OnClick(R.id.fab)
    fun addName() {
        presenter.onFabClicked()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = controller.adapter
        controller.requestModelBuild()
    }
}
