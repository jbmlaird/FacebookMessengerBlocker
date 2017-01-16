package bj.drunkmessengerblocker;

import android.app.Application;
import android.content.Context;

/**
 * Created by j on 15/01/2017.
 */

public class App extends Application
{
    private static AppComponent component;
    public static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setupGraph();
        context = getApplicationContext();
    }

    private void setupGraph()
    {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.inject(this);
    }

    public static AppComponent component()
    {
        return component;
    }
}
