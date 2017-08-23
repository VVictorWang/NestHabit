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
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyViewHolder> {
    private boolean isChoose;
    private Context mContext;
    private boolean isChoosing;
    private List<String> tickedMembers = new ArrayList<>();

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatar, avatar_choosen;
        private ImageView delete;
        private TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            name = (TextView) itemView.findViewById(R.id.name);
            avatar_choosen = (CircleImageView) itemView.findViewById(R.id.avatar_choosen);
        }
    }

    public MemberListAdapter(Context context, boolean isChoose, boolean isChoosing) {
        this.isChoose = isChoose;
        mContext = context;
        this.isChoosing = isChoosing;
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
            if (!isChoosing) {
                holder.avatar_choosen.setVisibility(View.GONE);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout
                                .delete_dialog, null);
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
            } else {
                holder.delete.setVisibility(View.GONE);
                holder.avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.avatar_choosen.getVisibility() == View.INVISIBLE) {
                            holder.avatar_choosen.setVisibility(View.VISIBLE);
                            holder.name.setTextColor(AppUtils.getAppContext().getResources()
                                    .getColor(R.color
                                    .red));
                            tickedMembers.add(holder.name.getText().toString());
                        } else {
                            holder.avatar_choosen.setVisibility(View.INVISIBLE);
                            holder.name.setTextColor(AppUtils.getAppContext().getResources()
                                    .getColor(R.color
                                    .mygray));
                            tickedMembers.remove(holder.name.getText().toString());
                        }
                    }
                });
            }

        }

    }

    public List<String> getChoosenMembers() {
        if (!isChoose && isChoosing && tickedMembers != null && !tickedMembers.isEmpty()) {
            return tickedMembers;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
