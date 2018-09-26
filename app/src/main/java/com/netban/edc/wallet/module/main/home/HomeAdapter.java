package com.netban.edc.wallet.module.main.home;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.view.widget.RoundImageView;

import java.util.List;

/**
 * Created by Evan on 2018/8/3.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private OnItemClick onItemClick;
    private List<CollageListBean.DataBean> mlist;
    private Context context;
    public HomeAdapter(Context context){
        this.context=context;
    }

    public void setMlist(List<CollageListBean.DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        CollageListBean.DataBean dataBean = mlist.get(position);

        holder.tv_name_college.setText(dataBean.getZh_name());
        holder.tv_number.setText(StringUtils.doubleToString(dataBean.getBalance())+context.getString(R.string.unit_trade));
        Glide.with(context).load(dataBean.getIcon()).error(R.drawable.ic_launcher).into(holder.img_icon);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(mlist.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist==null?0:mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        private RoundImageView img_icon;
        private TextView tv_number,tv_name_college;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            img_icon=itemView.findViewById(R.id.img_icon);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_name_college=itemView.findViewById(R.id.tv_name_college);
        }
    }

    public interface OnItemClick<T>{
        void onItemClick(T t,int pos);
    }
}
