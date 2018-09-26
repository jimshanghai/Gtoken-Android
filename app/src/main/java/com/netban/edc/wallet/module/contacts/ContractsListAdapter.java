package com.netban.edc.wallet.module.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.view.widget.RingImageView;
import com.netban.edc.wallet.view.widget.ReMoveLayout;

import java.util.List;

/**
 * Created by Evan on 2018/8/16.
 */

public class ContractsListAdapter extends RecyclerView.Adapter {
    private List<ContractsListBean.DataBean> mlist;
    private Context context;
    private OnItemClick onItemClick;
    public ContractsListAdapter(Context context){
        this.context=context;
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setMlist(List<ContractsListBean.DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contracts, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ContractsListBean.DataBean dataBean = mlist.get(position);
            ViewHolder mholder=((ViewHolder) holder);
            mholder.tv_numbers.setText(context.getString(R.string.number_person) + dataBean.getNumbers());
            mholder.tv_name_user.setText(dataBean.getUsername());
            Glide.with(context).load(dataBean.getAvatar()).error(R.drawable.ic_launcher).into(mholder.img_avater);
            mholder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick == null) return;
                    onItemClick.onItemClick(dataBean, position);
                }
            });
            mholder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mholder.view.close();
                    if (onItemClick == null) return;
                    onItemClick.onDelete(dataBean, position);
                }
            });


    }

    @Override
    public int getItemCount() {
        return mlist==null?0:mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RingImageView img_avater;
        private TextView tv_name_user,tv_numbers;
        private SwipeLayout view;
        private TextView btn_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view= (SwipeLayout) itemView;
            img_avater=itemView.findViewById(R.id.img_avater);
            tv_name_user=itemView.findViewById(R.id.tv_name_user);
            tv_numbers=itemView.findViewById(R.id.tv_numbers);
            btn_delete=itemView.findViewById(R.id.btn_delete);
        }
    }

    interface OnItemClick{
        void onItemClick(ContractsListBean.DataBean dataBean,int pos);
        void onDelete(ContractsListBean.DataBean dataBean,int pos);
    }

}
