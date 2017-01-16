package bj.drunkmessengerblocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by j on 15/01/2017.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupComponent(App.component());
    }

    protected abstract void setupComponent(AppComponent appComponent);
}