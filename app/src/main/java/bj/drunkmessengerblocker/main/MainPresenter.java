package bj.drunkmessengerblocker.main;

import javax.inject.Inject;

import bj.drunkmessengerblocker.ActivityScope;

/**
 * Created by j on 16/01/2017.
 */

@ActivityScope
public class MainPresenter implements MainContract.Presenter
{
    private MainContract.View view;

    @Inject
    public MainPresenter(MainContract.View view)
    {
        this.view = view;
    }
}
