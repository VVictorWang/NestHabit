package com.victor.nesthabit.ui.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.view.SwitchButton;

/**
 * Created by victor on 7/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class VibrateVolumeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIBRATE_TYPE = 1;
    private static final int VOLUME_TYPE = 2;
    private static final int PROFILE_MUSIC = 3;
    private Context mContext;
    private int profileposiition = -1;
    private MediaPlayer mMediaPlayer;
    private String musicUri = null, musicName = null;

    public VibrateVolumeAdapter(Context context, int profileposiition, String musicUri, String
            musicName) {
        mContext = context;
        this.profileposiition = profileposiition;
        this.musicUri = musicUri;
        this.musicName = musicName;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIBRATE_TYPE) {
            return new ViberateViewHoler(LayoutInflater.from(parent.getContext()).inflate(R
                    .layout.vibrate_layout, null));
        } else if (viewType == VOLUME_TYPE) {
            return new VolumeViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .volume_layout, null));
        } else if (viewType == PROFILE_MUSIC) {
            return new MusicListAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.music_list_adapter, null));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIBRATE_TYPE;
        } else if (position == 1) {
            return VOLUME_TYPE;
        } else {
            return PROFILE_MUSIC;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == VOLUME_TYPE) {
            ((VolumeViewHoler) holder).progressBar.setOnSeekBarChangeListener(new SeekBar
                    .OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    AudioManager manager = (AudioManager
                            ) mContext.getSystemService(Context.AUDIO_SERVICE);
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager
                            .FLAG_PLAY_SOUND);
                    ((VolumeViewHoler) holder).percent.setText(progress + "%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } else if (type == PROFILE_MUSIC && profileposiition == -1) {
            ((MusicListAdapter.MyViewHolder) holder).isChecked.setVisibility(View.VISIBLE);
            ((MusicListAdapter.MyViewHolder) holder).name.setText(musicName);
            playMusic(musicUri);
        }
    }

    private void playMusic(String data) {
        data = data.substring(data.indexOf('/') + 2);
        Uri uri = Uri.parse(data);
        mMediaPlayer = MediaPlayer.create(mContext, uri);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public void stopMusic() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
    }

    @Override
    public int getItemCount() {
        if (profileposiition == -1 && musicUri != null && musicName != null) {
            return 3;
        } else {
            return 2;
        }
    }

    static class ViberateViewHoler extends RecyclerView.ViewHolder {
        private SwitchButton vibrate;

        public ViberateViewHoler(View itemView) {
            super(itemView);
            vibrate = (SwitchButton) itemView.findViewById(R.id.toogle);
        }
    }

    static class VolumeViewHoler extends RecyclerView.ViewHolder {
        private SeekBar progressBar;
        private TextView percent;

        public VolumeViewHoler(View itemView) {
            super(itemView);
            progressBar = (SeekBar) itemView.findViewById(R.id.progress_bar);
            percent = (TextView) itemView.findViewById(R.id.volume_percent);
        }
    }
}
