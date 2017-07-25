package com.victor.nesthabit.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.view.CircleImageView;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyViewHolder> {
    private boolean isChoose;
    private Context mContext;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatar;
        private ImageView delete;
        private TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public MemberListAdapter( Context context,boolean isChoose) {
        this.isChoose = isChoose;
        mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (!isChoose) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .member_list_adapter, null);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_small,
                    null);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!isChoose) {
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.delete_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setView(view);
                    Button ok = (Button) view.findViewById(R.id.delete);
                    Button cancel = (Button) view.findViewById(R.id.cancel);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
