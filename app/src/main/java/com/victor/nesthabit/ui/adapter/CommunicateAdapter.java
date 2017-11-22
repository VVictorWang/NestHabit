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
import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
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
    private List<ChatInfo> mCommunicateItems;
    private RecyclerView mRecyclerView;

    public CommunicateAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        mCommunicateItems = new ArrayList<>();
        mRecyclerView = recyclerView;
    }

    public void addItem(ChatInfo item) {
        mCommunicateItems.add(item);
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mCommunicateItems.size() - 1);
    }

    public void addAll(List<ChatInfo> communicateItems) {
        mCommunicateItems.clear();
        mCommunicateItems.addAll(communicateItems);
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mCommunicateItems.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LEFT_TYPE) {
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
        ChatInfo item = mCommunicateItems.get(position);
        String day = getDayForSection(position);
        if (position == getPositionForSection(day)) {
            if (type == LEFT_TYPE) {
                ((LeftViewHolder) holder).date_layout.setVisibility(View.VISIBLE);
                ((LeftViewHolder) holder).date.setText(DateUtils.formatString(item.getCreatedAt()
                        , "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
            } else if (type == RIGHT_TYPR) {
                ((RightViewHoler) holder).date_layout.setVisibility(View.VISIBLE);
                ((RightViewHoler) holder).date.setText(DateUtils.formatCreatedAt(item
                                .getCreatedAt(),
                        "yyyy-MM-dd"));
                ((RightViewHoler) holder).text.setText(item.getContents());
                ((RightViewHoler) holder).time.setText(DateUtils.formatCreatedAt(item
                        .getCreatedAt(), "HH:mm"));
            }
        } else {
            if (type == LEFT_TYPE) {
                ((LeftViewHolder) holder).date_layout.setVisibility(View.GONE);
            } else if (type == RIGHT_TYPR) {
                ((RightViewHoler) holder).date_layout.setVisibility(View.GONE);
                ((RightViewHoler) holder).text.setText(item.getContents());
                ((RightViewHoler) holder).time.setText(DateUtils.formatCreatedAt(item
                        .getCreatedAt(), "HH:mm"));
            }
        }

    }


    @Override
    public int getItemCount() {
        return mCommunicateItems.size();
    }

    public String getDayForSection(int position) {
        String time = mCommunicateItems.get(position).getCreatedAt();
        return DateUtils.formatString(time, "yyyy-MM-dd HH:mm:ss", "dd");
    }

    public int getPositionForSection(String day) {
        for (int i = 0; i < getItemCount(); i++) {
            String time = mCommunicateItems.get(i).getCreatedAt();
            String dayString = DateUtils.formatString(time, "yyyy-MM-dd HH:mm:ss", "dd");
            if (dayString.equals(day)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        ChatInfo item = mCommunicateItems.get(position);
        if (item.getUserId().equals(Utils.getUserId())) {
            return RIGHT_TYPR;
        }
        return LEFT_TYPE;
    }


    private static class LeftViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView name, date;
        private TextView time;
        private TextView text;
        private RelativeLayout left_layout, date_layout;

        LeftViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            text = (TextView) itemView.findViewById(R.id.text);
            date = (TextView) itemView.findViewById(R.id.date);
            left_layout = (RelativeLayout) itemView.findViewById(R.id.left_layout);
            date_layout = (RelativeLayout) itemView.findViewById(R.id.date_layout);
        }
    }

    private static class RightViewHoler extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView time, date;
        private RelativeLayout right_layout, date_layout;

        RightViewHoler(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            time = (TextView) itemView.findViewById(R.id.time);
            date = (TextView) itemView.findViewById(R.id.date);
            right_layout = (RelativeLayout) itemView.findViewById(R.id.right_layout);
            date_layout = (RelativeLayout) itemView.findViewById(R.id.date_layout);
        }
    }


}
