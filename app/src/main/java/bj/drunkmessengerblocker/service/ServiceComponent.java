package bj.drunkmessengerblocker.service;

import bj.drunkmessengerblocker.ActivityScope;
import bj.drunkmessengerblocker.AppComponent;
import dagger.Component;

@ActivityScope
@Component(modules = {ServiceModule.class}, dependencies = {AppComponent.class})
public interface ServiceComponent
{
    void inject(MyAccessibilityService myAccessibilityService);
}
