package bj.drunkmessengerblocker.blacklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import javax.inject.Inject;

import bj.drunkmessengerblocker.R;
import bj.drunkmessengerblocker.utils.SharedPrefsManager;

/**
 * Created by j on 16/01/2017.
 */
public class BlacklistPresenter implements BlacklistContract.Presenter
{
    private BlacklistContract.View view;
    @Inject Context context;
    private ArrayList<String> names;
    private SharedPrefsManager sharedPrefsManager;
    private MaterialDialog dialog;

    @Inject
    public BlacklistPresenter(BlacklistContract.View view, SharedPrefsManager sharedPrefsManager)
    {
        this.view = view;
        this.names = sharedPrefsManager.getStoredNames();
        this.sharedPrefsManager = sharedPrefsManager;
    }

    @Override
    public void initialiseRecyclerView(RecyclerView recyclerView)
    {
        recyclerView.setAdapter(new MyRecyclerViewAdapter(names, context, sharedPrefsManager));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onFabClicked()
    {
        dialog = new MaterialDialog.Builder((BlacklistActivity) view)
                .title("Enter name to add")
                .content("You must put their name as what appears on Facebook Messenger. If they have a nickname, they won't be blocked.")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(2, 20, R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback()
                {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {
                        names.add(dialog.getInputEditText().getText().toString());
                        sharedPrefsManager.storeName(dialog.getInputEditText().getText().toString());
                    }
                })
                .input("Name", null, new MaterialDialog.InputCallback()
                {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input)
                    {
                        // Do something
                    }
                }).show();
    }
}
