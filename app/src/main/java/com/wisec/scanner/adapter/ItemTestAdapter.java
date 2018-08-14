package com.wisec.scanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.FailBean;

import java.util.List;

/**
 * Created by qwe on 2018/4/24.
 */

public class ItemTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FailBean> list;//存放数据
    Context context;
    View headerView;

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_NORMAL = 2;
//    private View headerView;

    public ItemTestAdapter(List<FailBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new RecyclerView.ViewHolder(headerView) {
            };
        }
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fail_layout, parent, false));
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
        FailBean failBean = list.get(position);

        holder.timeTextView.setText(failBean.getTime());
        holder.resultTextView.setText(failBean.getResult());
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    /*之下的方法都是为了方便操作，并不是必须的*/

    //在指定位置插入，原位置的向后移动一格
    public boolean addItem(int position, FailBean failBean) {
        if (position < list.size() && position >= 0) {
            list.add(position, failBean);
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
    public boolean updateItem(int position, FailBean failBean) {
        if (position < list.size() && position >= 0) {
            list.remove(position);
            list.add(position, failBean);
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
        TextView timeTextView, resultTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.tv_time);
            resultTextView = itemView.findViewById(R.id.tv_result);
        }
    }
}
