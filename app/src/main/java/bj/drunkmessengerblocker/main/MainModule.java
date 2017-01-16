package bj.drunkmessengerblocker.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by j on 15/01/2017.
 */
@Module
public class MainModule
{
    private MainContract.View mainView;

    public MainModule(MainContract.View mainView)
    {
        this.mainView = mainView;
    }

    @Provides
    MainContract.View providesMainContractView()
    {
        return mainView;
    }
}
