package com.victor.nesthabit.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NextRankTotalAdapter extends RecyclerView.Adapter<NextRankTotalAdapter.MyViewHolder>{
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView rankNumber;
        private ImageView avatar;
        private TextView name;
        private TextView day;

        public MyViewHolder(View itemView) {
            super(itemView);
            rankNumber = (TextView) itemView.findViewById(R.id.rank_number);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            day = (TextView) itemView.findViewById(R.id.day);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .rank_list_adapter_follow, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.rankNumber.setText(String.format("%02d", (position + 4)));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
