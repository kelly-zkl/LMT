package com.wisec.scanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.EventBean;

import java.util.List;

/**
 * Created by qwe on 2018/4/11.
 */

public class ItemEventAdapter extends RecyclerView.Adapter<ItemEventAdapter.MyViewHolder> {
    List<EventBean> list;
    Context context;

    public ItemEventAdapter(List<EventBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventBean eventBean = list.get(position);
        holder.pciTv.setText(eventBean.getPci() + "");
        holder.earfcnTv.setText(eventBean.getEarfcn() + "");
        holder.timeTv.setText(eventBean.getTime());
        if (eventBean.getCause() == 0) {
            holder.reasonTv.setText("Cell selection");
        } else if (eventBean.getCause() == 1) {
            holder.reasonTv.setText("Cell reselection");
        } else if (eventBean.getCause() == 2) {
            holder.reasonTv.setText("Handover");
        } else if (eventBean.getCause() == 3) {
            holder.reasonTv.setText("Redirection");
        }
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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView timeTv, earfcnTv, pciTv, reasonTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.tv_time);
            earfcnTv = itemView.findViewById(R.id.tv_earfcn);
            pciTv = itemView.findViewById(R.id.tv_pci);
            reasonTv = itemView.findViewById(R.id.tv_reason);
        }
    }
}
