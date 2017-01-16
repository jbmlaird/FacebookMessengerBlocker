package bj.drunkmessengerblocker;

import android.content.Context;

import javax.inject.Singleton;

import bj.drunkmessengerblocker.utils.SharedPrefsManager;
import dagger.Component;

/**
 * Created by j on 15/01/2017.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent
{
    void inject(App app);

    Context getApplicationContext();

    SharedPrefsManager getSharedPrefsManager();
}
