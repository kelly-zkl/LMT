package com.wisec.scanner.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.SignalBean;

import java.util.List;

/**
 * Created by qwe on 2018/6/20.
 */

public class ItemSignalAdapter extends RecyclerView.Adapter<ItemSignalAdapter.MyViewHolder> {
    List<SignalBean> list;
    Context context;

    public ItemSignalAdapter(List<SignalBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_signal_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SignalBean signalBean = list.get(position);
        holder.nameTv.setText(signalBean.getName());
        holder.typeTv.setVisibility(View.GONE);
//        holder.typeTv.setText(signalBean.getType());
        holder.timeTv.setText(signalBean.getTime());
        if (signalBean.isUp()) {
            holder.upIv.setVisibility(View.GONE);
            holder.downIv.setVisibility(View.GONE);
        } else {
            holder.upIv.setVisibility(View.GONE);
            holder.downIv.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent(signalBean.getContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //清空显示数据
    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }

    private AlertDialog dialog;
    private TextView mTvContent;

    private void showContent(String content) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(context).create();

            View dialogView = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_item_signal_content, null);

            mTvContent = dialogView.findViewById(R.id.tv_content);
            dialogView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.setView(dialogView);
        }
        mTvContent.setText(content);
        dialog.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView timeTv, nameTv, typeTv;
        ImageView upIv, downIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.tv_time);
            nameTv = itemView.findViewById(R.id.tv_name);
            typeTv = itemView.findViewById(R.id.tv_type);
            upIv = itemView.findViewById(R.id.img_up);
            downIv = itemView.findViewById(R.id.img_down);
        }
    }
}
