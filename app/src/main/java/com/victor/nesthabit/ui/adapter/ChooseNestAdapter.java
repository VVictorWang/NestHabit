package com.victor.nesthabit.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ChooseNestAdapter extends RecyclerView.Adapter<ChooseNestAdapter.ListViewHolder> {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<NestInfo> mBirdCageInfos;
    public static final String TAG = "@victor NestLisAdapter";
    private ListViewHolder tickholer;




    static class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView birdcageListImage;
        private TextView birdcageListText;
        private CardView mCardView;
        private CardView redborder;


        ListViewHolder(View itemView) {
            super(itemView);
            birdcageListImage = (ImageView) itemView.findViewById(R.id.birdcage_list_image);
            birdcageListText = (TextView) itemView.findViewById(R.id.birdcage_list_text);
            mCardView = (CardView) itemView.findViewById(R.id.card);
            redborder = (CardView) itemView.findViewById(R.id.card_background);
        }

    }


    public ChooseNestAdapter(Context context, RecyclerView recyclerView, List<NestInfo>
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
        view = inflater.inflate(R.layout.nestlist_choose, parent, false);
        holder = new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        NestInfo info = mBirdCageInfos.get(position);
        holder.birdcageListText.setText(info.getName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.redborder.getVisibility() == View.INVISIBLE) {
                    holder.redborder.setVisibility(View.VISIBLE);
                    if (tickholer != null) {
                        if (tickholer != holder) {
                            tickholer.redborder.setVisibility(View.INVISIBLE);
                        }
                        tickholer = holder;
                    }else
                        tickholer = holder;
                }else
                    holder.redborder.setVisibility(View.INVISIBLE);
            }
        });

    }

    public String getNestName() {
        if (tickholer != null) {
            return tickholer.birdcageListText.getText().toString();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mBirdCageInfos.size();
    }


}
