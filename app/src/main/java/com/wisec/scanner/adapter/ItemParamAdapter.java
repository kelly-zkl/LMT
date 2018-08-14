package com.wisec.scanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.ParamBean;

import java.util.List;

/**
 * 频点优先级
 * Created by qwe on 2018/5/11.
 */

public class ItemParamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ParamBean> list;//存放数据
    Context context;
    View headerView;

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_NORMAL = 2;

    public ItemParamAdapter(List<ParamBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new RecyclerView.ViewHolder(headerView) {
            };
        }
        ItemParamAdapter.MyViewHolder holder = new ItemParamAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_param_layout, parent, false));
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
        if (!(rHolder instanceof ItemParamAdapter.MyViewHolder)) {
            return;
        }
        position = position - 1;
        MyViewHolder holder = (MyViewHolder) rHolder;
        ParamBean paramBean = list.get(position);
        if (paramBean.isShow()) {
            holder.titleNameTv.setText(paramBean.getC_earfcn() + "(" + paramBean.getC_pci() + ") - " + paramBean.getEarfcn());
            holder.titlePloyTv.setText("从" + paramBean.getC_earfcn() + "到" + paramBean.getEarfcn() + "的重选策略");
            holder.titleGravityTv.setText(paramBean.getEarfcn() + "的重选优先级");

            if (paramBean.getPloy().contains("高优先级")) {
                holder.highView.setVisibility(View.VISIBLE);
                holder.equalView.setVisibility(View.GONE);
                holder.lowView.setVisibility(View.GONE);
            } else if (paramBean.getPloy().contains("低优先级")) {
                holder.highView.setVisibility(View.GONE);
                holder.equalView.setVisibility(View.GONE);
                holder.lowView.setVisibility(View.VISIBLE);
            } else {//同等优先级
                holder.highView.setVisibility(View.GONE);
                holder.equalView.setVisibility(View.VISIBLE);
                holder.lowView.setVisibility(View.GONE);
            }

            holder.valuePloyTv.setText(paramBean.getPloy());
            holder.valueHighTv.setText(paramBean.getHigh());
            holder.valueEqualTv.setText(paramBean.getEqual());
            holder.valueLowTv.setText(paramBean.getLow());
            holder.valueGravityTv.setText(paramBean.getLev() + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    /*之下的方法都是为了方便操作，并不是必须的*/

    //在指定位置插入，原位置的向后移动一格
    public void addItem(ParamBean paramBean) {
        list.add(paramBean);
        notifyDataSetChanged();
    }

    //去除指定位置的子项
    public void removeItem(ParamBean paramBean) {
        list.remove(paramBean);
        notifyDataSetChanged();
    }

    //去除指定位置的子项
    public void updateItem(int position, ParamBean paramBean) {
        if (position < list.size() && position >= 0) {
            list.set(position, paramBean);
            notifyDataSetChanged();
        }
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
        View highView, equalView, lowView;
        TextView titleNameTv, titlePloyTv, valuePloyTv, valueHighTv, valueEqualTv, valueLowTv,
                titleGravityTv, valueGravityTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            highView = itemView.findViewById(R.id.view_high);
            equalView = itemView.findViewById(R.id.view_equal);
            lowView = itemView.findViewById(R.id.view_low);

            titleNameTv = itemView.findViewById(R.id.tv_title_name);
            titlePloyTv = itemView.findViewById(R.id.tv_title_ploy);
            titleGravityTv = itemView.findViewById(R.id.tv_title_gravity);

            valuePloyTv = itemView.findViewById(R.id.tv_value_ploy);
            valueHighTv = itemView.findViewById(R.id.tv_value_high);
            valueEqualTv = itemView.findViewById(R.id.tv_value_max);
            valueLowTv = itemView.findViewById(R.id.tv_value_low);
            valueGravityTv = itemView.findViewById(R.id.tv_value_gravity);
        }
    }
}
