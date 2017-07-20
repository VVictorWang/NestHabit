package com.victor.nesthabit.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;

import java.util.List;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TOP_VIEW = 1;
    public static final int CONT_VIEW = 2;

    static class TopViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private ImageView rankNumberImage;
        private ImageView avatar;
        private TextView name;
        public TopViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            rankNumberImage = (ImageView) itemView.findViewById(R.id.rank_number_image);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private TextView rankNumber;
        private ImageView avatar;
        private TextView name;
        private TextView day;

        public MyViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            rankNumber = (TextView) itemView.findViewById(R.id.rank_number);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            day = (TextView) itemView.findViewById(R.id.day);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TOP_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_list_adapter,
                    null);
        } else if (viewType == CONT_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .rank_list_adapter_follow, null);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 1 || position == 2)
            return TOP_VIEW;
        else
            return CONT_VIEW;
    }

}
