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

import static com.victor.nesthabit.R.id.people;
import static com.victor.nesthabit.R.id.progress_text;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class BirdCaseFragAdapter extends RecyclerView.Adapter<BirdCaseFragAdapter.ListViewHolder> {
    private Context mContext;
    private RecyclerView mRecyclerView;

    private List<BirdCageInfo> mBirdCageInfos;


    static class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView birdcageListImage;
        private TextView birdcageListText;
        private TextView progresstext;
        private TextView peoplea;


        ListViewHolder(View itemView) {
            super(itemView);
            birdcageListImage = (ImageView) itemView.findViewById(R.id.birdcage_list_image);
            birdcageListText = (TextView) itemView.findViewById(R.id.birdcage_list_text);
            progresstext = (TextView) itemView.findViewById(progress_text);
            peoplea = (TextView) itemView.findViewById(people);
        }

    }


    public BirdCaseFragAdapter(Context context, RecyclerView recyclerView, List<BirdCageInfo>
            birdCageInfos) {
        mContext = context;
        mRecyclerView = recyclerView;
        mBirdCageInfos = birdCageInfos;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        ListViewHolder holder;
        view = inflater.inflate(R.layout.birdcagelist, parent, false);
        holder = new ListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity((Activity) mContext, NestSpecificActivity.class);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        BirdCageInfo info = mBirdCageInfos.get(position);
        holder.progresstext.setText(info.getDay_insist() + "/" + info
                .getDay_total());
        holder.birdcageListText.setText(info.getInfo());
        holder.peoplea.setText("+" + info.getPeople() + "人");

    }

    @Override
    public int getItemCount() {
        return mBirdCageInfos.size();
    }


}
