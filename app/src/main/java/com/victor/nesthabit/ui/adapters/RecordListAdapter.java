package com.victor.nesthabit.ui.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.RecordItem;
import com.victor.nesthabit.ui.fragments.PlayMusicFragment;
import com.victor.nesthabit.service.RecordingService;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by victor on 7/5/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ListViewHolder> implements RecordingService.OnNewRecordListenner {
    private Context mContext;
    private List<RecordItem> mRecordItems;
    private static final String TAG = "RecordListAdapter";
    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView vName;
        private TextView vLength;
        private TextView vDateAdded;
        private View cardView;

        public ListViewHolder(View itemView) {
            super(itemView);
            vName = (TextView) itemView.findViewById(R.id.file_name_text);
            vLength = (TextView) itemView.findViewById(R.id.file_length_text);
            vDateAdded = (TextView) itemView.findViewById(R.id.file_date_added_text);
            cardView = itemView.findViewById(R.id.card_record_list);
        }
    }

    public RecordListAdapter(Context context, List<RecordItem> recordItems) {
        mContext = context;
        mRecordItems = recordItems;
        PlayMusicFragment.setOnNewRecordListenner(this);
    }

    @Override
    public void onNewRecordAdded(RecordItem item) {

    }
//
//    @Override
//    public void onNewRecordAddedtoDataBase(RecordItem item) {
//        mRecordItems.add(item);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void onRecordDeleted(RecordItem item) {
//        mRecordItems.remove(item);
//        notifyDataSetChanged();
//    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_file_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        final RecordItem recordItem = mRecordItems.get(position);
        long itemDuration = recordItem.getLength();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration)
                - TimeUnit.MINUTES.toSeconds(minutes);

        holder.vName.setText(recordItem.getName());
        holder.vLength.setText(String.format("%02d:%02d", minutes, seconds));
        holder.vDateAdded.setText(
                DateUtils.formatDateTime(
                        mContext,
                        recordItem.getTime_added(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR
                )
        );
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlayMusicFragment playbackFragment =
                            new PlayMusicFragment().newInstance(recordItem,PlayMusicFragment.STATUS_SAVED);
                    FragmentTransaction transaction = ((FragmentActivity) mContext)
                            .getSupportFragmentManager()
                            .beginTransaction();

                    playbackFragment.show(transaction, "dialog_playback");

                } catch (Exception e) {
                    Log.e(TAG, "exception", e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecordItems.size();
    }
}
