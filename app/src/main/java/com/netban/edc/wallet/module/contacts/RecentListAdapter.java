package com.netban.edc.wallet.module.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.view.widget.RingImageView;

import java.util.List;

/**
 * Created by Evan on 2018/8/31.
 */

public class RecentListAdapter extends RecyclerView.Adapter<RecentListAdapter.RViewHolder> {
    private List<ContractsListBean.DataBean> mlist;
    private Context context;
    private ContractsListAdapter.OnItemClick onItemClick;
    private boolean isRecent;
    public RecentListAdapter(Context context){
        this.context=context;
    }

    public ContractsListAdapter.OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(ContractsListAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setMlist(List<ContractsListBean.DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @Override
    public RecentListAdapter.RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contracts_recent, parent, false);
            return new RecentListAdapter.RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentListAdapter.RViewHolder rholder, int position) {
            ContractsListBean.DataBean dataBean = mlist.get(position);
            rholder.tv_numbers.setText(context.getString(R.string.number_person) + dataBean.getNumbers());
            rholder.tv_name_user.setText(dataBean.getUsername());
            Glide.with(context).load(dataBean.getAvatar()).error(R.drawable.ic_launcher).into(rholder.img_avater);
            rholder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick == null) return;
                    onItemClick.onItemClick(dataBean, position);
                }
            });


    }

    @Override
    public int getItemCount() {
        return mlist==null?0:mlist.size();
    }

    class RViewHolder extends RecyclerView.ViewHolder{
        private RingImageView img_avater;
        private TextView tv_name_user,tv_numbers;
        private LinearLayout view;

        public RViewHolder(View itemView) {
            super(itemView);
            this.view= (LinearLayout) itemView;
            img_avater=itemView.findViewById(R.id.img_avater);
            tv_name_user=itemView.findViewById(R.id.tv_name_user);
            tv_numbers=itemView.findViewById(R.id.tv_numbers);

        }
    }

    interface OnItemClick{
        void onItemClick(ContractsListBean.DataBean dataBean,int pos);
    }

}
