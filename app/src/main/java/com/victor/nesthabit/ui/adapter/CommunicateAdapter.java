package com.victor.nesthabit.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.SendMessageResponse;
import com.victor.nesthabit.util.DateUtils;

import java.util.List;

/**
 * Created by victor on 7/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class CommunicateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int DATE_TYPE = 1;
    public static final int LEFT_TYPE = 2;
    public static final int RIGHT_TYPR = 3;
    private Context mContext;
    private List<SendMessageResponse> mCommunicateItems;

    public void addItem(SendMessageResponse item) {
        mCommunicateItems.add(item);
        notifyDataSetChanged();
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
        int type = holder.getItemViewType();
        SendMessageResponse item = mCommunicateItems.get(position);
        if (type == DATE_TYPE) {
            ((DateViewHoler) holder).date.setText(DateUtils.format(item.getTime(), "yyyy-MM-dd"));
        } else if (type == LEFT_TYPE) {

        } else if (type == RIGHT_TYPR) {
            ((RightViewHoler) holder).text.setText(item.getValue());
            ((RightViewHoler) holder).time.setText(DateUtils.format(item.getTime(), "HH:mm"));
        }
    }

    public CommunicateAdapter(Context context, List<SendMessageResponse> communicateItems) {
        mContext = context;
        mCommunicateItems = communicateItems;
    }

    @Override
    public int getItemCount() {
        return mCommunicateItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        SendMessageResponse item = mCommunicateItems.get(position);
        return item.getType();
    }

    private static class DateViewHoler extends RecyclerView.ViewHolder {
        private TextView date;

        DateViewHoler(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    private static class LeftViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView name;
        private TextView time;
        private TextView text;

        LeftViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private static class RightViewHoler extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView time;

        RightViewHoler(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }


}
