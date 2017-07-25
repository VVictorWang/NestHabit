package com.victor.nesthabit.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.victor.nesthabit.R;
import com.victor.nesthabit.view.SwitchButton;

/**
 * Created by victor on 7/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class VibrateVolumeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIBRATE_TYPE = 1;
    public static final int VOLUME_TYPE = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIBRATE_TYPE) {
            return new ViberateViewHoler(LayoutInflater.from(parent.getContext()).inflate(R
                    .layout.vibrate_layout, null));
        } else if (viewType == VOLUME_TYPE) {
            return new VolumeViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .volume_layout, null));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIBRATE_TYPE;
        } else
            return VOLUME_TYPE;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class ViberateViewHoler extends RecyclerView.ViewHolder {
        private SwitchButton vibrate;

        public ViberateViewHoler(View itemView) {
            super(itemView);
            vibrate = (SwitchButton) itemView.findViewById(R.id.toogle);
        }
    }

    static class VolumeViewHoler extends RecyclerView.ViewHolder {
        private CardView vibrateLayout;
        private RelativeLayout volumeLayout;
        private SeekBar progressBar;

        public VolumeViewHoler(View itemView) {
            super(itemView);
            vibrateLayout = (CardView) itemView.findViewById(R.id.vibrate_layout);
            volumeLayout = (RelativeLayout) itemView.findViewById(R.id.volume_layout);
            progressBar = (SeekBar) itemView.findViewById(R.id.progress_bar);
        }
    }
}
