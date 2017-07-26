package com.victor.nesthabit.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.activity.NestSpecificActivity;
import com.victor.nesthabit.ui.presenter.AddNestPresenter;
import com.victor.nesthabit.ui.presenter.NestListPresenter;
import com.victor.nesthabit.util.ActivityManager;

import java.util.List;

import static android.os.Build.VERSION_CODES.N;
import static com.victor.nesthabit.R.id.people;
import static com.victor.nesthabit.R.id.progress_text;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class NestListFragAdapter extends RecyclerView.Adapter<NestListFragAdapter.ListViewHolder>
        implements AddNestPresenter.OnCageDataChanged, NestListPresenter.onNestInfoAdded {
    private Context mContext;
    private RecyclerView mRecyclerView;

    private List<NestInfo> mBirdCageInfos;

    @Override
    public void OnDataAdded(NestInfo cageInfo) {
        mBirdCageInfos.add(cageInfo);
        notifyDataSetChanged();
    }


    @Override
    public void addNestInfos(List<NestInfo> nestInfos) {
        mBirdCageInfos.addAll(nestInfos);
        notifyDataSetChanged();
    }


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


    public NestListFragAdapter(Context context, RecyclerView recyclerView, List<NestInfo>
            birdCageInfos) {
        mContext = context;
        mRecyclerView = recyclerView;
        mBirdCageInfos = birdCageInfos;
        AddNestPresenter.setOnCageDataChanged(this);
        NestListPresenter.setOnNestInfoAdded(this);
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
        NestInfo info = mBirdCageInfos.get(position);
        holder.progresstext.setText(info.getDay_insist() + "/" + info
                .getChallenge_days());
        holder.birdcageListText.setText(info.getName());
        holder.peoplea.setText("+" + info.getMembers_amount() + "人");

    }

    @Override
    public int getItemCount() {
        return mBirdCageInfos.size();
    }


}
