package com.victor.nesthabit.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;

/**
 * Created by victor on 7/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DaKaWallAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int DATE_TYPE = 1;
    public static final int CONTENT_TYPE = 2;
    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DATE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .daka_date_adapter, null);
            return new DataViewHoler(view);
        } else if (viewType == CONTENT_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .daka_wall_adapter, null);
            return new ContentViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            if (holder instanceof DataViewHoler) {
                ((DataViewHoler) holder).viewFirst.setVisibility(View.INVISIBLE);
            } else {
                ((ContentViewHolder)holder).viewFirst.setVisibility(View.INVISIBLE);
            }
        } else if (position == getItemCount()) {
            ((ContentViewHolder) holder).viewlast.setVisibility(View.INVISIBLE);
        }

    }

    public DaKaWallAdapater(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 3) {
            return DATE_TYPE;
        } else {
            return CONTENT_TYPE;
        }
    }

    static class DataViewHoler extends RecyclerView.ViewHolder {
        private TextView viewFirst;
        private ImageView yellowCircle;
        private TextView date;
        public DataViewHoler(View itemView) {
            super(itemView);
            viewFirst = (TextView) itemView.findViewById(R.id.view_first);
            yellowCircle = (ImageView) itemView.findViewById(R.id.yellow_circle);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout leftLayout;
        private TextView viewFirst;
        private ImageView avatar;
        private TextView name;
        private TextView time;
        private TextView text;
        private TextView viewlast;
        public ContentViewHolder(View itemView) {
            super(itemView);
            leftLayout = (RelativeLayout) itemView.findViewById(R.id.left_layout);
            viewFirst = (TextView) itemView.findViewById(R.id.view_first);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            text = (TextView) itemView.findViewById(R.id.text);
            viewlast = (TextView) itemView.findViewById(R.id.view_last);
        }
    }





}
