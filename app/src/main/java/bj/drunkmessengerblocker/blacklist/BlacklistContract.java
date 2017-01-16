package bj.drunkmessengerblocker.blacklist;

import android.support.v7.widget.RecyclerView;

/**
 * Created by j on 16/01/2017.
 */

public interface BlacklistContract
{
    interface View
    {

    }

    interface Presenter
    {
        void initialiseRecyclerView(RecyclerView recyclerView);

        void onFabClicked();
    }
}
