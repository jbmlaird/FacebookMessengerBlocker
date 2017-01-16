package bj.drunkmessengerblocker.main;

import bj.drunkmessengerblocker.ActivityScope;
import bj.drunkmessengerblocker.AppComponent;
import dagger.Component;

/**
 * Created by j on 15/01/2017.
 */

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {MainModule.class})
public interface MainComponent
{
    void inject(MainActivity mainActivity);
}
