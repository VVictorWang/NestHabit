package com.victor.nesthabit.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.activity.MusicProfileActivity;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.utils.MusicManger;
import com.victor.nesthabit.view.SwitchButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by victor on 7/18/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MusicSettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIBRATE_VOLUME_TYPE = 1;
    public static final int PROFILE_TYPE = 3;
    public static final int OTHER_TYPE = 4;
    private Context mContext;
    public static final String TAG = "@victor ListAdapter";
    private MusicListAdapter mMusicListAdapter;


    public MusicSettingAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIBRATE_VOLUME_TYPE;
        else if (position == 1)
            return PROFILE_TYPE;
        else
            return OTHER_TYPE;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIBRATE_VOLUME_TYPE) {
            return new VibrateVolumeViewHoler(LayoutInflater.from(parent.getContext()).inflate(R
                    .layout.music_list_adpatera, null));
        } else if (viewType == PROFILE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R
                    .layout.profile_music_layout, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.startActivity((Activity) mContext, MusicProfileActivity.class);
                }
            });
            return new ProfileViewHolder(view);
        } else if (viewType == OTHER_TYPE) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.music_list_adpatera, null));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == OTHER_TYPE) {
            ((MyViewHolder) holder).mRecyclerView.setLayoutManager(new LinearLayoutManager
                    (mContext));
            mMusicListAdapter = new MusicListAdapter(mContext, ((MyViewHolder) holder)
                    .mRecyclerView, false);
            ((MyViewHolder) holder).mRecyclerView.setAdapter(mMusicListAdapter);

        } else if (type == VIBRATE_VOLUME_TYPE) {
            ((VibrateVolumeViewHoler) holder).mRecyclerView.setLayoutManager(new
                    LinearLayoutManager(mContext));
            ((VibrateVolumeViewHoler) holder).mRecyclerView.setAdapter(new VibrateVolumeAdapter());
        }


    }

    public void stopPlaying() {
        if (mMusicListAdapter != null) {
            mMusicListAdapter.stopPlaying();
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }


    static class VibrateVolumeViewHoler extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;

        public VibrateVolumeViewHoler(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.music_list);
        }

    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder {

        public ProfileViewHolder(View itemView) {
            super(itemView);

        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;

        MyViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.music_list);
        }
    }


}
