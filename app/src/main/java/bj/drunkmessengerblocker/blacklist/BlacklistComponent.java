package bj.drunkmessengerblocker.blacklist;

import bj.drunkmessengerblocker.ActivityScope;
import bj.drunkmessengerblocker.AppComponent;
import dagger.Component;

/**
 * Created by j on 16/01/2017.
 */
@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {BlacklistModule.class})
public interface BlacklistComponent
{
    void inject(BlacklistActivity activity);
}
