package com.victor.nesthabit.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;

/**
 * Created by victor on 7/18/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder> {
    public static final String TAG = "@victor ListAdapter";
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Cursor mCursor;
    private MyViewHolder tickedHoler;
//    private boolean isProfile;
    private MediaPlayer mMediaPlayer;
    //    private int profileposition = -1;
    private String musicUri = null;
    private int myPosition = -1;


    public MusicListAdapter(Context context, RecyclerView recyclerView, boolean isProfile, String
            musicUri) {
        mContext = context;
        mRecyclerView = recyclerView;
//        this.isProfile = isProfile;
        this.musicUri = musicUri;
        if (!isProfile) {
            mCursor = context.getContentResolver().query(MediaStore.Audio.Media
                            .INTERNAL_CONTENT_URI, null, null,
                    null, null);
        } else {
            mCursor = context.getContentResolver().query(MediaStore.Audio.Media
                    .EXTERNAL_CONTENT_URI, null, null, null, null);
        }
    }

    private int getPosition() {
        if (musicUri == null) {
            return -1;
        }
        mCursor.moveToFirst();
        int i = 0;
        do {
            if (mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio
                    .Media.DATA)).equals(musicUri)) {
                return i;
            }
            i++;
        } while (mCursor.moveToNext());
        return -1;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myPosition = getPosition();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.music_list_adapter, null));

    }

    private void notifyOthers(MyViewHolder holder) {
        if (tickedHoler != null) {
            tickedHoler.isChecked.setVisibility(View.INVISIBLE);
        }
        holder.isChecked.setVisibility(View.VISIBLE);
        tickedHoler = holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            holder.name.setText(mCursor.getString(mCursor.getColumnIndex
                    (MediaStore.Audio.Media
                            .TITLE)));
            holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.isChecked.getVisibility() == View.INVISIBLE) {
                        holder.isChecked.setVisibility(View.VISIBLE);
                        mCursor.moveToPosition(position);
                        notifyOthers(holder);
                        String data = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio
                                .Media.DATA));
                        musicUri = data;
                        playMusic(data);
                    }
                }
            });
            if (position == myPosition) {
                tickedHoler = holder;
                holder.isChecked.setVisibility(View.VISIBLE);
                String data = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio
                        .Media.DATA));
                musicUri = data;
                playMusic(data);
            }
        }

    }

    private void playMusic(String data) {
        Uri uri = Uri.parse(data);
        Log.d(TAG, uri.toString());
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
        mMediaPlayer = MediaPlayer.create(mContext, uri);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    public void stopPlaying() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
    }

    public String getMusic() {
        if (tickedHoler != null) {
            return tickedHoler.name.getText().toString();
        }
        return null;
    }

    public String getMusicUri() {
        if (musicUri != null) {
            return musicUri;
        }
        return null;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView isChecked;
        public RelativeLayout mRelativeLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.music_name);
            isChecked = (ImageView) itemView.findViewById(R.id.istick);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.music_layout);
        }
    }


}
