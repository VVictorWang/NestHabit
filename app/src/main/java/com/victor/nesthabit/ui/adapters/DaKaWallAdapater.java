package com.victor.nesthabit.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

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
        public DataViewHoler(View itemView) {
            super(itemView);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        public ContentViewHolder(View itemView) {
            super(itemView);
        }
    }


}
