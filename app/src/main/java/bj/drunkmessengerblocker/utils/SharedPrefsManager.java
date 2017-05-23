package bj.drunkmessengerblocker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.drunkmessengerblocker.R;

/**
 * Created by j on 16/01/2017.
 */
@Singleton
public class SharedPrefsManager
{
    private Context context;
    private SharedPreferences settings;

    @Inject
    public SharedPrefsManager(Context context)
    {
        this.context = context;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public ArrayList<String> getStoredNames()
    {
        ArrayList<String> storedNames = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            String storedName = settings.getString(context.getString(R.string.stored_name) + i, "");
            if (storedName.equals(""))
                break;
            storedNames.add(storedName);
        }
        return storedNames;
    }

    public void storeName(String name)
    {
        int numberOfNamesStored = 0;

        // Gets number of names stored
        for (int i = 0; i < 10; i++)
        {
            String storedName = settings.getString(context.getString(R.string.stored_name) + i, "");
            if (storedName.equals(""))
            {
                numberOfNamesStored = i;
                break;
            }
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.stored_name) + numberOfNamesStored, name);

        editor.apply();
    }
}
