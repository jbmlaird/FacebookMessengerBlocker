package bj.drunkmessengerblocker.service;

import dagger.Module;

/**
 * Created by Josh Laird on 23/05/2017.
 */
@Module
public class ServiceModule
{
    private MyAccessibilityService service;

    public ServiceModule(MyAccessibilityService service)
    {
        this.service = service;
    }
}
