package com.victor.nesthabit.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.activity.AddAlarmActivity;
import com.victor.nesthabit.ui.activity.NestSpecificActivity;
import com.victor.nesthabit.data.BirdCageInfo;
import com.victor.nesthabit.utils.ActivityManager;

import java.util.List;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class BirdCaseFragAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private RecyclerView mRecyclerView;

    private List<BirdCageInfo> mBirdCageInfos;

    private static int NEWBIRDCAGE = 1;
    private static int BIRDCAGELIST = 2;


    private static class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView birdcageListImage;
        private TextView birdcageListText;
        private TextView progress_text;
        private TextView people;


        ListViewHolder(View itemView) {
            super(itemView);
            birdcageListImage = (ImageView) itemView.findViewById(R.id.birdcage_list_image);
            birdcageListText = (TextView) itemView.findViewById(R.id.birdcage_list_text);
            progress_text = (TextView) itemView.findViewById(R.id.progress_text);
            people = (TextView) itemView.findViewById(R.id.people);
        }

    }

    private class NewBirdCageViewHolder extends RecyclerView.ViewHolder {
        private ImageView create;

        NewBirdCageViewHolder(View itemView) {
            super(itemView);
            create = (ImageView) itemView.findViewById(R.id.creat_new);
        }
    }

    public BirdCaseFragAdapter(Context context, RecyclerView recyclerView, List<BirdCageInfo>
            birdCageInfos) {
        mContext = context;
        mRecyclerView = recyclerView;
        mBirdCageInfos = birdCageInfos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        RecyclerView.ViewHolder holder;
        if (viewType == NEWBIRDCAGE) {
            view = inflater.inflate(R.layout.creat_birdcage, parent, false);
            holder = new NewBirdCageViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.startActivity((Activity) mContext, AddAlarmActivity.class);
                }
            });
        } else {
            view = inflater.inflate(R.layout.birdcagelist, parent, false);
            holder = new ListViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.startActivity((Activity) mContext, NestSpecificActivity.class);
                }
            });

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == BIRDCAGELIST) {
            BirdCageInfo info = mBirdCageInfos.get(position);
            ((ListViewHolder) holder).progress_text.setText(info.getDay_insist() + "/" + info
                    .getDay_total());
            ((ListViewHolder) holder).birdcageListText.setText(info.getInfo());
            ((ListViewHolder) holder).people.setText(info.getPeople() + "人");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return NEWBIRDCAGE;
        } else {
            return BIRDCAGELIST;
        }
    }

    @Override
    public int getItemCount() {
        return mBirdCageInfos.size() + 1;
    }


}
