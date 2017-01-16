package bj.drunkmessengerblocker.blacklist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import javax.inject.Inject;

import bj.drunkmessengerblocker.AppComponent;
import bj.drunkmessengerblocker.BaseActivity;
import bj.drunkmessengerblocker.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by j on 16/01/2017.
 */

public class BlacklistActivity extends BaseActivity implements BlacklistContract.View
{
    private final String TAG = "BlacklistActivity";

    private BlacklistComponent component;
    @Inject BlacklistPresenter presenter;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        ButterKnife.bind(this);

        presenter.initialiseRecyclerView(recyclerView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void setupComponent(AppComponent appComponent)
    {
        component = DaggerBlacklistComponent.builder()
                .appComponent(appComponent)
                .blacklistModule(new BlacklistModule(this))
                .build();

        component.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.fab)
    public void addName()
    {
        presenter.onFabClicked();
    }
}
