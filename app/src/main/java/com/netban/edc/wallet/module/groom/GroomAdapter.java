package com.netban.edc.wallet.module.groom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.GroomBean;

import java.util.List;

/**
 * Created by Evan on 2018/9/18.
 */

public class GroomAdapter extends RecyclerView.Adapter<GroomAdapter.ViewHolder> {
    private Context context;
    private List<GroomBean.DataBean> mlist;
    public GroomAdapter(Context context){
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_groom, parent, false);
        return new ViewHolder(view);
    }
    public void setMlist(List<GroomBean.DataBean> list){
        mlist=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position==0){
            holder.tv_name.setText(context.getString(R.string.name_groom));
            holder.tv_numbers.setText(context.getString(R.string.numbers_groom));
            holder.tv_time.setText(context.getString(R.string.time_groom));
            return;
        }
        GroomBean.DataBean groomBean = mlist.get(position);
        holder.tv_name.setText(groomBean.getName());
        holder.tv_numbers.setText(groomBean.getNumbers());
        holder.tv_time.setText(groomBean.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return mlist==null?1:mlist.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name,tv_numbers,tv_time;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_numbers=itemView.findViewById(R.id.tv_numbers);
            tv_time=itemView.findViewById(R.id.tv_time);

        }
    }
}
