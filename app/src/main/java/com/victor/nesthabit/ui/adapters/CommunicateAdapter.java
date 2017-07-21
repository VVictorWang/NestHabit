package com.victor.nesthabit.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;

/**
 * Created by victor on 7/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class CommunicateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int DATE_TYPE = 1;
    public static final int LEFT_TYPE = 2;
    public static final int RIGHT_TYPR = 3;
    private Context mContext;

    public CommunicateAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DATE_TYPE) {
            return new DateViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .communicate_date, null));
        } else if (viewType == LEFT_TYPE) {
            return new LeftViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .communicate_adapter_left, null));
        } else if (viewType == RIGHT_TYPR) {
            return new RightViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .communicate_adapter_right, null));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return DATE_TYPE;
        } else if (position % 2 == 0) {
            return RIGHT_TYPR;
        }else
            return LEFT_TYPE;
    }

    private static class DateViewHoler extends RecyclerView.ViewHolder {
         DateViewHoler(View itemView) {
            super(itemView);
        }
    }

    private static class LeftViewHolder extends RecyclerView.ViewHolder {
        LeftViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class RightViewHoler extends RecyclerView.ViewHolder {
         RightViewHoler(View itemView) {
            super(itemView);
        }
    }


}
