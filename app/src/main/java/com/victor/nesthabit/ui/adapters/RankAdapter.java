package com.victor.nesthabit.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
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
    private Context mContext;

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


    static class NextViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;
        public NextViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.list);
        }
    }

    public RankAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == TOP_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_list_adapter,
                    null);
            return new TopViewHolder(view);
        } else if (viewType == CONT_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .rank_list_adapter_next, null);
            return new NextViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((TopViewHolder) holder).rankNumberImage.setImageDrawable(mContext.getDrawable(R
                    .drawable.first));
            ((TopViewHolder) holder).avatar.setImageDrawable(mContext.getDrawable(R
                    .drawable.wang));
        } else if (position == 1) {
            ((TopViewHolder) holder).rankNumberImage.setImageDrawable(mContext.getDrawable(R
                    .drawable.second));
            ((TopViewHolder) holder).avatar.setImageDrawable(mContext.getDrawable(R.drawable
                    .huang));
            ((TopViewHolder) holder).card.setBackgroundColor(mContext.getResources().getColor(R.color.second_rank));

        } else if (position == 2) {
            ((TopViewHolder) holder).rankNumberImage.setImageDrawable(mContext.getDrawable(R
                    .drawable.third));
            ((TopViewHolder) holder).avatar.setImageDrawable(mContext.getDrawable(R.drawable
                    .jia));
        } else {
            ((NextViewHolder) holder).mRecyclerView.setLayoutManager(new LinearLayoutManager
                    (mContext));
            ((NextViewHolder) holder).mRecyclerView.setAdapter(new NextRankTotalAdapter());
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 1 || position == 2)
            return TOP_VIEW;
        else
            return CONT_VIEW;
    }

}
