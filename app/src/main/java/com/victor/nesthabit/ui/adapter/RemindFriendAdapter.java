package com.victor.nesthabit.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.RecordItem;
import com.victor.nesthabit.ui.activity.ChooseActivity;
import com.victor.nesthabit.ui.presenter.AddRemindPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.view.CircleImageView;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RemindFriendAdapter extends RecyclerView.Adapter<RemindFriendAdapter.MyViewHolder>
        implements AddRemindPresenter.OnNewRecordChanged {
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private List<RecordItem> mRecordItems = new ArrayList<>();
    private boolean isPlaying = false;
    private String nestId = null;

    public RemindFriendAdapter(Context context, String nestId) {
        mContext = context;
        mRecordItems = DataSupport.findAll(RecordItem.class);
        AddRemindPresenter.setOnNewRecordChanged(this);
        this.nestId = nestId;
    }

    @Override
    public void onNewAdded(RecordItem recordItem) {
        mRecordItems.add(recordItem);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .remind_list_adapter, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecordItem recordItem = mRecordItems.get(position);
        if (!recordItem.isVoice()) {
            holder.contentLayout.setVisibility(View.VISIBLE);
            holder.voiceTextStatus.setText(mContext.getString(R.string.text_remind));
            holder.play.setVisibility(View.INVISIBLE);
            holder.content.setText(recordItem.getContent());
        } else {
            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPlay(isPlaying, recordItem, holder.play);
                    isPlaying = !isPlaying;
                }
            });
        }
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChooseActivity.class);
                intent.putExtra("id", recordItem.getId());
                intent.putExtra("nestId", nestId);
                ActivityManager.startActivity((Activity) mContext, intent);
            }
        });
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.delete_dialog, null);
                TextView textView = (TextView) view.findViewById(R.id.delete_text);
                Button cancel = (Button) view.findViewById(R.id.cancel);
                Button ensure = (Button) view.findViewById(R.id.delete);
                textView.setText(mContext.getString(R.string.delete_remind));
                AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();
                dialog.show();
                ensure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecordItems.remove(recordItem);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
    }

    private void onPlay(boolean isPlaying, RecordItem recordItem, CircleImageView play) {
        if (!isPlaying) {
            if (mMediaPlayer == null) {
                startPlaying(recordItem, play); //start from beginning
            } else {
                resumePlaying(play); //resume the currently paused MediaPlayer
            }

        } else {
            //pause the MediaPlayer
            pausePlaying(play);
        }
    }

    private void startPlaying(RecordItem recordItem, CircleImageView play) {
        play.setImageResource(R.drawable.pause);
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(recordItem.getFile_path());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying(play);
            }
        });

    }

    private void pausePlaying(CircleImageView play) {
        play.setImageResource(R.drawable.play);
        mMediaPlayer.pause();
    }

    private void resumePlaying(CircleImageView play) {
        play.setImageResource(R.drawable.pause);
        mMediaPlayer.start();
    }

    private void stopPlaying(ImageView play) {
        play.setImageResource(R.drawable.play);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
        isPlaying = !isPlaying;
    }

    @Override
    public int getItemCount() {
        return mRecordItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView play;
        private RelativeLayout voiceTextLayout;
        private TextView voiceTextStatus;
        private TextView duixiang;
        private ImageView avatar;
        private TextView name;
        private TextView startTime;
        private TextView endTime;
        private RelativeLayout contentLayout;
        private TextView content;
        private CardView mCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            play = (CircleImageView) itemView.findViewById(R.id.play);
            voiceTextLayout = (RelativeLayout) itemView.findViewById(R.id.voice_text_layout);
            voiceTextStatus = (TextView) itemView.findViewById(R.id.voice_text_status);
            duixiang = (TextView) itemView.findViewById(R.id.duixiang);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            startTime = (TextView) itemView.findViewById(R.id.start_time);
            endTime = (TextView) itemView.findViewById(R.id.end_time);
            contentLayout = (RelativeLayout) itemView.findViewById(R.id.content_layout);
            content = (TextView) itemView.findViewById(R.id.content);
            mCardView = (CardView) itemView.findViewById(R.id.card);
        }
    }
}
