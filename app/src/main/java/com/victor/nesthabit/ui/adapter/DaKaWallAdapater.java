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
import com.victor.nesthabit.bean.DakaResponse;
import com.victor.nesthabit.ui.activity.ShareActivity;
import com.victor.nesthabit.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DaKaWallAdapater extends RecyclerView.Adapter<DaKaWallAdapater.ContentViewHolder>
        implements ShareActivity.OnDakaAdded {

    private Context mContext;
    private List<DakaResponse> mDakaResponses;

    public DaKaWallAdapater(Context context) {
        mContext = context;
        mDakaResponses = new ArrayList<>();
        ShareActivity.setOnDakaAdded(this);
    }

    public void addItem(DakaResponse daysBean) {
        mDakaResponses.add(daysBean);
        notifyDataSetChanged();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .daka_wall_adapter, null);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        DakaResponse bean = mDakaResponses.get(position);
        String time = getDayForSection(position);
        if (position == getPositionForSection(time)) {
            holder.datelayout.setVisibility(View.VISIBLE);
            holder.date.setText(DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
        }
        holder.name.setText(bean.username);
        holder.text.setText(bean.comment);
        if (position == 0) {
            holder.dateViewFirst.setVisibility(View.INVISIBLE);
        }
        if (position == (getItemCount() - 1)) {
            holder.viewlast.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mDakaResponses.size();
    }

    public String getDayForSection(int position) {
        long time = mDakaResponses.get(position).created_time;
        return DateUtils.format(time, "yyyyMMdd");
    }

    public int getPositionForSection(String day) {
        for (int i = 0; i < getItemCount(); i++) {
            long time = mDakaResponses.get(i).created_time;
            String dayString = DateUtils.format(time, "yyyyMMdd");
            if (dayString.equals(day)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void OnDakaItemAdded(DakaResponse dakaResponse) {
        addItem(dakaResponse);
    }


    static class ContentViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout datelayout, dakaLayout;
        private RelativeLayout leftLayout;
        private TextView viewFirst;
        private ImageView avatar;
        private TextView name;
        private TextView time;
        private TextView text;
        private TextView viewlast;

        private TextView date, dateViewFirst;

        public ContentViewHolder(View itemView) {
            super(itemView);
            datelayout = (RelativeLayout) itemView.findViewById(R.id.date_layout);
            dakaLayout = (RelativeLayout) itemView.findViewById(R.id.daka_layout);
            leftLayout = (RelativeLayout) itemView.findViewById(R.id.left_layout);
            viewFirst = (TextView) itemView.findViewById(R.id.view_first);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            text = (TextView) itemView.findViewById(R.id.text);
            viewlast = (TextView) itemView.findViewById(R.id.view_last);

            dateViewFirst = (TextView) itemView.findViewById(R.id.date_view_first);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }


}
