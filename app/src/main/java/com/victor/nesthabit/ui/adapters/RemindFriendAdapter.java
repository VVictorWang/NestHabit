package com.victor.nesthabit.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.view.CircleImageView;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RemindFriendAdapter extends RecyclerView.Adapter<RemindFriendAdapter.MyViewHolder> {
    private Context mContext;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView play;
        private RelativeLayout voiceTextLayout;
        private TextView voiceTextStatus;
        private RelativeLayout layoutTwo;
        private TextView duixiang;
        private ImageView avatar;
        private TextView name;
        private RelativeLayout layoutThree;
        private TextView startTimeText;
        private TextView startTime;
        private RelativeLayout layoutFour;
        private TextView endTimeText;
        private TextView endTime;
        private RelativeLayout contentLayout;
        private TextView contentText;
        private TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            play = (CircleImageView) itemView.findViewById(R.id.play);
            voiceTextLayout = (RelativeLayout) itemView.findViewById(R.id.voice_text_layout);
            voiceTextStatus = (TextView) itemView.findViewById(R.id.voice_text_status);
            layoutTwo = (RelativeLayout) itemView.findViewById(R.id.layout_two);
            duixiang = (TextView) itemView.findViewById(R.id.duixiang);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            layoutThree = (RelativeLayout) itemView.findViewById(R.id.layout_three);
            startTimeText = (TextView) itemView.findViewById(R.id.start_time_text);
            startTime = (TextView) itemView.findViewById(R.id.start_time);
            layoutFour = (RelativeLayout) itemView.findViewById(R.id.layout_four);
            endTimeText = (TextView) itemView.findViewById(R.id.end_time_text);
            endTime = (TextView) itemView.findViewById(R.id.end_time);
            contentLayout = (RelativeLayout) itemView.findViewById(R.id.content_layout);
            contentText = (TextView) itemView.findViewById(R.id.content_text);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public RemindFriendAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .remind_list_adapter, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position % 3 == 0) {
            holder.contentLayout.setVisibility(View.VISIBLE);
            holder.voiceTextStatus.setText(mContext.getString(R.string.text_remind));
            holder.play.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
