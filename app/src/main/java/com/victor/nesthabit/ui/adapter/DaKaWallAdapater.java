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
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by victor on 7/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DaKaWallAdapater extends RecyclerView.Adapter<DaKaWallAdapater.ContentViewHolder> {

    private Context mContext;
    private List<PunchInfo> mPunchInfoList;

    public DaKaWallAdapater(Context context) {
        mContext = context;
        mPunchInfoList = new ArrayList<>();
    }

    public void addItem(PunchInfo punchInfo) {
        mPunchInfoList.add(punchInfo);
        Collections.sort(mPunchInfoList, (o1, o2) -> {
            long o1Created = DateUtils.stringToLong(o1.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
            long o2Created = DateUtils.stringToLong(o2.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
            if (o1Created == o2Created) {
                return 0;
            } else if (o1Created > o2Created) {
                return 1;
            }
            return -1;
        });
        notifyDataSetChanged();
    }

    public void clearData() {
        mPunchInfoList.clear();
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
        PunchInfo bean = mPunchInfoList.get(position);
        String time = getDayForSection(position);
        if (position == getPositionForSection(time)) {
            holder.datelayout.setVisibility(View.VISIBLE);
            holder.date.setText(DateUtils.formatString(bean.getCreatedAt(), "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd"));
        }
        UserRepository.getInstance().getUserInfo(bean.getUserId(), new NetWorkBoundUtils.CallBack<UserInfo>() {
            @Override
            public void callSuccess(Observable<UserInfo> result) {
                result.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userInfo -> {
                            holder.name.setText(userInfo.getUsername());
                        }, throwable -> {
                        });
            }

            @Override
            public void callFailure(String errorMessage) {

            }
        });
        holder.text.setText(bean.getContents());
        if (position == 0) {
            holder.dateViewFirst.setVisibility(View.INVISIBLE);
        }
        if (position == (getItemCount() - 1)) {
            holder.viewlast.setVisibility(View.INVISIBLE);
        }
        holder.time.setText(DateUtils.formatString(bean.getCreatedAt(), "yyyy-MM-dd HH:mm:ss",
                "HH:mm"));
    }

    @Override
    public int getItemCount() {
        return mPunchInfoList.size();
    }

    public String getDayForSection(int position) {
        String time = mPunchInfoList.get(position).getCreatedAt();
        return DateUtils.formatString(time, "yyyy-MM-dd HH:mm:ss", "yyyyMMdd");
    }

    public int getPositionForSection(String day) {
        for (int i = 0; i < getItemCount(); i++) {
            String time = mPunchInfoList.get(i).getCreatedAt();
            String dayString = DateUtils.formatString(time, "yyyy-MM-dd HH:mm:ss", "yyyyMMdd");
            if (dayString.equals(day)) {
                return i;
            }
        }
        return -1;
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
