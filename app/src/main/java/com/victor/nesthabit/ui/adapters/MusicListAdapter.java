package com.victor.nesthabit.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;

import java.util.List;

/**
 * Created by victor on 7/18/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> musics;
    private RecyclerView mRecyclerView;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView isChecked;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.music_name);
            isChecked = (ImageView) itemView.findViewById(R.id.istick);
        }
    }

    public MusicListAdapter(Context context, List<String> musics,RecyclerView recyclerView) {
        mContext = context;
        this.musics = musics;
        mRecyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_adapter,
                null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isChecked.getVisibility() == View.INVISIBLE) {
                    holder.isChecked.setVisibility(View.VISIBLE);
                    notifyOthers(view);
                }
            }
        });
        return holder;
    }

    private void notifyOthers(View view) {
        for (int i = 0; i < getItemCount(); i++) {
            View child = mRecyclerView.getChildAt(i);
            if (view != child) {
                MyViewHolder holder = (MyViewHolder) mRecyclerView.getChildViewHolder(child);
                if (holder.isChecked.getVisibility() == View.VISIBLE) {
                    holder.isChecked.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(musics.get(position));
        if (position == 0) {
            holder.isChecked.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
