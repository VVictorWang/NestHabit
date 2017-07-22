package com.victor.nesthabit.ui.adapters;

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
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Cursor mCursor;
    private int tickedposition = 0;
    private boolean isProfile;
    private MediaPlayer mMediaPlayer;
    public static final String TAG = "@victor ListAdapter";


    public MusicListAdapter(Context context, RecyclerView recyclerView, boolean isProfile) {
        mContext = context;
        mRecyclerView = recyclerView;
        this.isProfile = isProfile;
        if (!isProfile) {
            mCursor = context.getContentResolver().query(MediaStore.Audio.Media
                            .INTERNAL_CONTENT_URI, null, null,
                    null, null);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.music_list_adapter, null));

    }

    private void notifyOthers(int position) {
        MyViewHolder holder = (MyViewHolder) mRecyclerView.findViewHolderForAdapterPosition
                (tickedposition);
        holder.isChecked.setVisibility(View.INVISIBLE);
        tickedposition = position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!isProfile && mCursor != null) {
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
                        notifyOthers(position);
                        String data = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio
                                .Media.DATA));
                        playMusic(data);
                    }
                }
            });
            if (position == 0 && !isProfile) {
                tickedposition = 0;
                ((MyViewHolder) holder).isChecked.setVisibility(View.VISIBLE);
                mCursor.moveToFirst();
                String data = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio
                        .Media.DATA));
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
        if (!isProfile) {
            return mCursor.getCount();
        } else
            return 1;
    }

    public void stopPlaying() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView isChecked;
        private RelativeLayout mRelativeLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.music_name);
            isChecked = (ImageView) itemView.findViewById(R.id.istick);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.music_layout);
        }
    }

    public String getMusic() {
        MyViewHolder holder = (MyViewHolder) mRecyclerView.findViewHolderForAdapterPosition
                (tickedposition);
        return holder.name.getText().toString();

    }

}
