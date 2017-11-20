package com.victor.nesthabit.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.ui.activity.AddAlarmActivity;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/17/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockListAdapter extends RecyclerView.Adapter<ClockListAdapter.MyViewHolder> {
    private static final String[] WEEK_DAYS = new String[]{"周日", "周一", "周二", "周三", "周四", "周五",
            "周六"};
    private Context mContext;
    private List<AlarmInfo> mAlarmInfos = new ArrayList<>();
    private int white;
    private int white_transparent;

    public ClockListAdapter(Context context) {
        mContext = context;
        white = mContext.getResources().getColor(R.color.white);
        white_transparent = mContext.getResources().getColor(R.color.while_transpante60);
        mAlarmInfos = new ArrayList<>();
    }


    public void addAlarm(AlarmInfo alarmInfo) {
        mAlarmInfos.add(alarmInfo);
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout
                .remind_clock_list_adapter, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AlarmInfo alarmInfo = mAlarmInfos.get(position);
        List<String> weekdays = new ArrayList<>();
        List<Integer> weeks = alarmInfo.getRepeat();
        for (int i = 0; i < weeks.size(); i++) {
            if (weeks.get(i) == 1) {
                weekdays.add(WEEK_DAYS[i]);
            }
        }
        ClockWeekAdpater adpater = new ClockWeekAdpater(weekdays);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.remindList.setLayoutManager(linearLayoutManager);
        holder.remindList.setAdapter(adpater);
        holder.remindTime.setText(String.format("%02d:%02d", alarmInfo.getHour(),
                alarmInfo.getMinute()));
        handleImageOn(alarmInfo, holder);
        holder.remindTitle.setText(alarmInfo.getTitle());
        holder.mSwitchButton.setOnToggleChanged(on -> {
            if (on) {
                holder.mCardView.setBackgroundColor(white);
                handleImageOn(alarmInfo, holder);

            } else {
                holder.mCardView.setBackgroundColor(white_transparent);
                handleImageOff(alarmInfo, holder);

            }
        });
        holder.mCardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddAlarmActivity.class);
            intent.putExtra("id", alarmInfo.getObjectId());
            ActivityManager.startActivity((Activity) mContext, intent);
        });
        holder.mCardView.setOnLongClickListener(v -> {
            View view = LayoutInflater.from(mContext).inflate(R.layout.delete_dialog, null);
            TextView textView = (TextView) view.findViewById(R.id.delete_text);
            Button cancel = (Button) view.findViewById(R.id.cancel);
            Button ensure = (Button) view.findViewById(R.id.delete);
            textView.setText(mContext.getString(R.string.delete_remind));
            AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();
            dialog.show();
            ensure.setOnClickListener(v12 -> {
//                        Observable<MsgResponse> observable = NestHabitApi.getInstance()
// .deleteAlarm
//                                (alarmTime.getMyid(), Utils.getHeader());
//                        observable.observeOn(AndroidSchedulers.mainThread())
//                                .subscribeOn(Schedulers.io())
//                                .subscribe(new Observer<MsgResponse>() {
//                                    @Override
//                                    public void onCompleted() {
//                                        mAlarmInfos.remove(alarmTime);
//                                        notifyDataSetChanged();
//                                        dialog.dismiss();
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//
//                                    }
//
//                                    @Override
//                                    public void onNext(MsgResponse msgResponse) {
//
//                                    }
//                                });
            });
            cancel.setOnClickListener(v1 -> dialog.dismiss());
            return false;
        });
    }

    private void handleImageOn(AlarmInfo alarmInfo, MyViewHolder holder) {
        if (DateUtils.isNight(alarmInfo.getHour())) {
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.night));
        } else
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.day));
    }

    private void handleImageOff(AlarmInfo alarmInfo, MyViewHolder holder) {
        if (DateUtils.isNight(alarmInfo.getHour())) {
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.night_19));
        } else
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.day_67));
    }

    @Override
    public int getItemCount() {
        return mAlarmInfos.size();
    }

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

    private class ClockWeekAdpater extends RecyclerView.Adapter<ClockWeekAdpater.ViewHolder> {
        private List<String> list;

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

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text_weekday);
            }
        }
    }


}
