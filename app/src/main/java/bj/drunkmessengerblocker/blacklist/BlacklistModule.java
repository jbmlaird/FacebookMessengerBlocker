package bj.drunkmessengerblocker.blacklist;

import dagger.Module;
import dagger.Provides;

/**
 * Created by j on 16/01/2017.
 */
@Module
public class BlacklistModule
{
    private final BlacklistContract.View view;

    public BlacklistModule(BlacklistContract.View view)
    {
        this.view = view;
    }

    @Provides
    BlacklistContract.View providesBlacklistView()
    {
        return view;
    }
}
