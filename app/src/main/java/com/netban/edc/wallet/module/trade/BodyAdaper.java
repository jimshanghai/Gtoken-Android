package com.netban.edc.wallet.module.trade;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.TradeListBean;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.view.widget.TradeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.netban.edc.wallet.base.Constant.Uri.PATH_EDC;

/**
 * Created by Evan on 2018/8/16.
 */

public class BodyAdaper extends RecyclerView.Adapter {
    private Context context;
    private List<TradeListBean.DataBean> mlist;
    private List<TradeListBean.DataBean> blist;
    public BodyAdaper(Context context){
        this.context=context;
    }
    private enum ItemType {
        HEAD_ADAPTER,
        BODY_BODY
    }

    public void setMlist(List<TradeListBean.DataBean> mlist) {
        this.mlist = mlist;
        this.blist=mlist;
        notifyDataSetChanged();
    }
    public void addMlist(List<TradeListBean.DataBean> mlist){
        if (blist==null)
            blist=new ArrayList<>();
        blist.addAll(mlist);
        setMlist(blist);
    }

    private void queryRefresh(List<TradeListBean.DataBean> mlist){
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ItemType.HEAD_ADAPTER.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_trade, parent, false);
            return new HeaderHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.body_list_trade, parent, false);
            return new BodyHolder(view);
        }
    }
    public List<TradeListBean.DataBean> query(String ms){
        List<TradeListBean.DataBean> list=new ArrayList<>();
        for (TradeListBean.DataBean dataBean:blist){
            if (dataBean.getName().contains(ms)){
                list.add(dataBean);
            }
        }
        return list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position!=0) {
            BodyHolder bodyHolder = (BodyHolder) holder;
            TradeListBean.DataBean dataBean = mlist.get(position-1);
            switch (dataBean.getState()){
                case 1:
                    bodyHolder.tv_status.setVisibility(View.GONE);
                    bodyHolder.tv_status.setTextColor(Color.GREEN);
                    bodyHolder.tv_status.setText(context.getString(R.string.success_status_trade));
                    break;
                case 2:
                    bodyHolder.tv_status.setVisibility(View.VISIBLE);
                    bodyHolder.tv_status.setTextColor(Color.BLUE);
                    bodyHolder.tv_status.setText(context.getString(R.string.confirmation_status_trade));
                    break;
                case 5:
                    bodyHolder.tv_status.setVisibility(View.VISIBLE);
                    bodyHolder.tv_status.setTextColor(Color.BLUE);
                    bodyHolder.tv_status.setText(context.getString(R.string.to_submitted_status_trade));
                    break;
                case 10:
                    bodyHolder.tv_status.setVisibility(View.VISIBLE);
                    bodyHolder.tv_status.setTextColor(Color.RED);
                    bodyHolder.tv_status.setText(context.getString(R.string.failure_status_trade));
                    break;
                default:
                    bodyHolder.tv_status.setVisibility(View.INVISIBLE);
                    bodyHolder.tradeLayout.setMsg("");
                    bodyHolder.tradeLayout.setIshow(false);
                    bodyHolder.tradeLayout.commit();
                    break;

            }

            bodyHolder.tv_name_college.setText(dataBean.getName());
            bodyHolder.tv_number.setText("No."+dataBean.getNumbers());
            bodyHolder.tv_time.setText(dataBean.getCreated_at());
            bodyHolder.tv_remark.setText(context.getString(R.string.remark_list_trade)+dataBean.getRemarks());
            bodyHolder.tv_txhash.setText(context.getString(R.string.txhash_list_trade)+dataBean.getTxhash());
            bodyHolder.tv_surplus.setText(context.getString(R.string.balance_trade)+":"+ StringUtils.doubleToString(dataBean.getAfter()));
            if (dataBean.getUsed_type()==1){
                ((BodyHolder) holder).tv_num_trade.setText("-"+dataBean.getUsed());
              ((BodyHolder) holder).tv_num_trade.setTextColor(Color.parseColor("#33C28C"));


            }else{

               ((BodyHolder) holder).tv_num_trade.setText("+"+dataBean.getUsed());
               ((BodyHolder) holder).tv_num_trade.setTextColor(Color.parseColor("#FA5250"));
            }
            if (dataBean.getAvatar().startsWith("/uploads")){
                Glide.with(context).load(PATH_EDC+dataBean.getAvatar()).error(R.drawable.ic_launcher).into(bodyHolder.img_collage);

            }else {
                Glide.with(context).load(dataBean.getAvatar()).error(R.drawable.ic_launcher).into(bodyHolder.img_collage);

            }
        }else{
            HeaderHolder headerHolder = (HeaderHolder) holder;
            ((HeaderHolder) holder).img_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String res = headerHolder.et_search.getText().toString();
                    if (TextUtils.isEmpty(res)) {
                        queryRefresh(blist);
                        return;
                    }
                    queryRefresh(query(res));
                }
            });

            headerHolder.et_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String res = s.toString();
                    if (TextUtils.isEmpty(res)) {
                        queryRefresh(blist);
                        return;
                    }
                    queryRefresh(query(res));
                }
            });

        }
    }

    private void updateTv(String s, TextView tv_num_trade,int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        tv_num_trade.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(color);
            }
        },4,s.length(),SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_num_trade.setText(builder);
    }


    @Override
    public int getItemCount() {
        return mlist==null?0:mlist.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return ItemType.HEAD_ADAPTER.ordinal();
        }else{
            return ItemType.BODY_BODY.ordinal();
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder{
        private EditText et_search;
        private ImageView img_search;
        public HeaderHolder(View itemView) {
            super(itemView);
            et_search=(EditText) itemView.findViewById(R.id.et_search);
            img_search= ((ImageView) itemView.findViewById(R.id.img_search));
        }
    }

    class BodyHolder extends RecyclerView.ViewHolder{
        private TextView tv_status,tv_name_college,tv_number,tv_time,tv_num_trade,tv_surplus,tv_remark,tv_txhash;
        private ImageView img_collage;
        private TradeLayout tradeLayout;
        public BodyHolder(View itemView) {
            super(itemView);
            tradeLayout= (TradeLayout) itemView;
            tv_status=(TextView) itemView.findViewById(R.id.tv_status);
            tv_name_college=(TextView) itemView.findViewById(R.id.tv_name_college);
            tv_number=(TextView) itemView.findViewById(R.id.tv_number);
            tv_time=(TextView) itemView.findViewById(R.id.tv_time);
            tv_num_trade=(TextView) itemView.findViewById(R.id.tv_num_trade);
            tv_surplus=(TextView) itemView.findViewById(R.id.tv_surplus);
            tv_remark=(TextView) itemView.findViewById(R.id.tv_remark);
            tv_txhash=(TextView) itemView.findViewById(R.id.tv_txhash);
            img_collage= ((ImageView) itemView.findViewById(R.id.img_collage));
        }
    }

}
