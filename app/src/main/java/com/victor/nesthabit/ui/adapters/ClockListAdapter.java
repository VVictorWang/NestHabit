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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by victor on 7/17/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockListAdapter extends RecyclerView.Adapter<ClockListAdapter.MyViewHolder> {
    private Context mContext;
    private List<List<String>> weekdays = new ArrayList<>();
    private int white;
    private int white_transparent;


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView remindTitle;
        private ImageView remindImage;
        private TextView remindTime;
        private RecyclerView remindList;
        private TextView remidTimeLeft;
        private SwitchButton mSwitchButton;
        private CardView mCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            remindTitle = (TextView) itemView.findViewById(R.id.remind_title);
            remindImage = (ImageView) itemView.findViewById(R.id.remind_image);
            remindTime = (TextView) itemView.findViewById(R.id.remind_time);
            remindList = (RecyclerView) itemView.findViewById(R.id.remind_list);
            remidTimeLeft = (TextView) itemView.findViewById(R.id.remid_time_left);
            mSwitchButton = (SwitchButton) itemView.findViewById(R.id.toogle);
            mCardView = (CardView) itemView.findViewById(R.id.clock_card);
        }
    }

    public ClockListAdapter(Context context) {
        mContext = context;
        white = mContext.getResources().getColor(R.color.white);
        white_transparent = mContext.getResources().getColor(R.color.while_transpante60);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout
                .remind_clock_list_adapter, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        List<String> list = new ArrayList<>();
        list.add("周一");
        list.add("周五");
        weekdays.add(list);
        ClockWeekAdpater adpater = new ClockWeekAdpater(weekdays.get(position));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.remindList.setLayoutManager(linearLayoutManager);
        holder.remindList.setAdapter(adpater);
        if (position == 2) {
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.night));
        }
        holder.mSwitchButton.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    holder.mCardView.setBackgroundColor(white);
                } else {
                    holder.mCardView.setBackgroundColor(white_transparent);
                    holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.day_67));
                    holder.mSwitchButton.setSpotColor(mContext.getResources().getColor(R.color
                            .spot_off));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private class ClockWeekAdpater extends RecyclerView.Adapter<ClockWeekAdpater.ViewHolder> {
        private List<String> list;

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text_weekday);
            }
        }

        public ClockWeekAdpater(List<String> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .clock_week_adapter, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
