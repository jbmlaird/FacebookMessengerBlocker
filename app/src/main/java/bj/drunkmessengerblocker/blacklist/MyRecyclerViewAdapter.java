package bj.drunkmessengerblocker.blacklist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bj.drunkmessengerblocker.R;
import bj.drunkmessengerblocker.utils.SharedPrefsManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by j on 16/01/2017.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>
{
    private ArrayList<String> names;
    private LayoutInflater inflater;
    private SharedPrefsManager sharedPrefsManager;

    public MyRecyclerViewAdapter(ArrayList<String> names, Context context, SharedPrefsManager sharedPrefsManager)
    {
        this.names = names;
        inflater = LayoutInflater.from(context);
        this.sharedPrefsManager = sharedPrefsManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.item_blacklist, parent, false);
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                activity.displayExpandedChanges();
            }
        });
        MyViewHolder holder = new MyViewHolder(itemView);
//        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.tvName.setText(names.get(position));
    }

    @Override
    public int getItemCount()
    {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tvName) TextView tvName;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
