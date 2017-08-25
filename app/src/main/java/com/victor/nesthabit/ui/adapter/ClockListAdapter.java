package com.victor.nesthabit.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.ui.activity.AddAlarmActivity;
import com.victor.nesthabit.ui.presenter.AddAlarmPresenter;
import com.victor.nesthabit.ui.presenter.ClockListPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.view.SwitchButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/17/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockListAdapter extends RecyclerView.Adapter<ClockListAdapter.MyViewHolder>
        implements AddAlarmPresenter.OnDataChanged, ClockListPresenter.onAlarmAdded {
    private static final String[] WEEK_DAYS = new String[]{"周日", "周一", "周二", "周三", "周四", "周五",
            "周六"};
    private Context mContext;
    private List<AlarmTime> mAlarmTimes = new ArrayList<>();
    private int white;
    private int white_transparent;

    public ClockListAdapter(Context context, List<AlarmTime> alarmTimes) {
        mContext = context;
        white = mContext.getResources().getColor(R.color.white);
        white_transparent = mContext.getResources().getColor(R.color.while_transpante60);
        mAlarmTimes = alarmTimes;
        AddAlarmPresenter.setOnDataChanged(this);
        ClockListPresenter.setOnAlarmAdded(this);
    }

    @Override
    public void OnDataAdded(AlarmTime alarmTime) {
        mAlarmTimes.add(alarmTime);
        notifyDataSetChanged();
    }

    @Override
    public void OnDataModified() {
        mAlarmTimes = DataSupport.findAll(AlarmTime.class);
        notifyDataSetChanged();
    }

    @Override
    public void AlarmAdded(AlarmTime alarmTime) {
        mAlarmTimes.add(alarmTime);
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
        AlarmTime alarmTime = mAlarmTimes.get(position);
        List<String> weekdays = new ArrayList<>();
        List<Integer> weeks = alarmTime.getWeeks();
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
        holder.remindTime.setText(String.format("%02d:%02d", alarmTime.getHour(), alarmTime
                .getMinute()));
        handleImageOn(alarmTime, holder);
        holder.remindTitle.setText(alarmTime.getTitle());

        holder.mSwitchButton.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    holder.mCardView.setBackgroundColor(white);
                    handleImageOn(alarmTime, holder);

                } else {
                    holder.mCardView.setBackgroundColor(white_transparent);
                    handleImageOff(alarmTime, holder);

                }
            }
        });
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddAlarmActivity.class);
                intent.putExtra("id", alarmTime.getId());
                ActivityManager.startActivity((Activity) mContext, intent);
            }
        });
//        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.delete_dialog,
//                        null);
//                TextView textView = (TextView) dialogView.findViewById(R.id.delete_text);
//                textView.setText(mContext.getText(R.string.ensure_delete));
//                Button delete = (Button) dialogView.findViewById(R.id.delete);
//
//            }
//        });
    }

    private void handleImageOn(AlarmTime alarmTime, MyViewHolder holder) {
        if (DateUtils.isNight(alarmTime.getHour())) {
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.night));
        } else
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.day));
    }

    private void handleImageOff(AlarmTime alarmTime, MyViewHolder holder) {
        if (DateUtils.isNight(alarmTime.getHour())) {
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.night_19));
        } else
            holder.remindImage.setImageDrawable(mContext.getDrawable(R.drawable.day_67));
    }

    @Override
    public int getItemCount() {
        return mAlarmTimes.size();
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
