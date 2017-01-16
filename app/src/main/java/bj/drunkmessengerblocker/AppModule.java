package bj.drunkmessengerblocker;

import android.content.Context;

import bj.drunkmessengerblocker.utils.SharedPrefsManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by j on 15/01/2017.
 */
@Module
public class AppModule
{
    private Context context;
    private SharedPrefsManager sharedPrefsManager;

    public AppModule(Context context)
    {
        this.context = context;
        this.sharedPrefsManager = new SharedPrefsManager(context);
    }

    @Provides
    Context providesContext()
    {
        return context;
    }

    @Provides
    SharedPrefsManager providesSharedPrefsManager()
    {
        return sharedPrefsManager;
    }
}
