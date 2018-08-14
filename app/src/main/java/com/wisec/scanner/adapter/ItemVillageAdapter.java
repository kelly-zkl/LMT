package com.wisec.scanner.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.VillageBean;
import com.wisec.scanner.customview.ProgressView;

import java.util.List;

/**
 * Created by kelly on 2018/4/11.
 */

public class ItemVillageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<VillageBean> list;//存放数据
    Context context;
    View headerView;

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_NORMAL = 2;
//    private View headerView;

    public ItemVillageAdapter(List<VillageBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new RecyclerView.ViewHolder(headerView) {
            };
        }
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_village_list, parent, false));
        return holder;
    }

    public View getHeaderView() {
        return headerView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rHolder, int position) {
        if (!(rHolder instanceof MyViewHolder)) {
            return;
        }
        position = position - 1;
        MyViewHolder holder = (MyViewHolder) rHolder;
        VillageBean villageBean = list.get(position);
        if (villageBean.getType() == VillageBean.LOCAL_CILLAGE) {//本小区\
            holder.sView.setVisibility(View.VISIBLE);
            holder.nView.setVisibility(View.GONE);
        } else {
            holder.sView.setVisibility(View.GONE);
            holder.nView.setVisibility(View.VISIBLE);
        }

        holder.bandTextView.setText(villageBean.getBand() + "");
        holder.pciTextView.setText(villageBean.getPci() + "");
        holder.earfcnTextView.setText(villageBean.getEarfcn() + "");
        int rsrq_pr = (int) Math.round(((30 + villageBean.getRsrq()) / 60) * 100);
        holder.rsrqTextView.setProgressBar(rsrq_pr, (int) villageBean.getRsrq() + "dB");
        holder.rsrpTextView.setProgressBar((int) (140 + villageBean.getRsrp()), (int) villageBean.getRsrp() + "dBm");
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    /*之下的方法都是为了方便操作，并不是必须的*/

    //在指定位置插入，原位置的向后移动一格
    public boolean addItem(int position, VillageBean villageBean) {
        if (position < list.size() && position >= 0) {
            list.add(position, villageBean);
            notifyItemInserted(position);
            return true;
        }
        return false;
    }

    //去除指定位置的子项
    public boolean removeItem(int position) {
        if (position < list.size() && position >= 0) {
            list.remove(position);
            notifyItemRemoved(position);
            return true;
        }
        return false;
    }

    //去除指定位置的子项
    public boolean updateItem(int position, VillageBean villageBean) {
        if (position < list.size() && position >= 0) {
            list.set(position, villageBean);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    //清空显示数据
    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View sView, nView;
        TextView bandTextView, pciTextView, earfcnTextView;
        ProgressView rsrpTextView, rsrqTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            sView = itemView.findViewById(R.id.tv_s);
            nView = itemView.findViewById(R.id.tv_n);
            bandTextView = itemView.findViewById(R.id.tv_band);
            pciTextView = itemView.findViewById(R.id.tv_pci);
            earfcnTextView = itemView.findViewById(R.id.tv_earfcn);
            rsrpTextView = itemView.findViewById(R.id.pb_rsrp);
            rsrqTextView = itemView.findViewById(R.id.pb_rsrq);
        }
    }
}
