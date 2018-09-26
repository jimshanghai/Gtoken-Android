package com.netban.edc.wallet.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.CollageListBean;

import java.util.List;

/**
 * Created by Evan on 2018/8/14.
 */

public class SpinerPopWindow<T> extends PopupWindow {
    private LayoutInflater inflater;
    private ListView mListView;
    private List<T> list;
    private MyAdapter  mAdapter;
    private Context context;
    public SpinerPopWindow(Context context, List<T> list, AdapterView.OnItemClickListener clickListener) {
        super(context);
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.list=list;
        init(clickListener);
    }

    private void init(AdapterView.OnItemClickListener clickListener){
        View view = inflater.inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(FrameLayout.LayoutParams.WRAP_CONTENT);
        setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setAdapter(mAdapter=new MyAdapter());
        mListView.setOnItemClickListener(clickListener);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
                holder=new ViewHolder();
                convertView=inflater.inflate(R.layout.spiner_item_layout, null);
                holder.view=(View) convertView.findViewById(R.id.view_root);
                holder.tvName=(TextView) convertView.findViewById(R.id.tv_name);
                holder.img_avater=((RoundImageView) convertView.findViewById(R.id.img_avater));
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder) convertView.getTag();
            }
            if (position==0){
                holder.view.setBackgroundColor(Color.parseColor("#F9FAFE"));
                Drawable drawable = context.getResources().getDrawable(R.drawable.pull_icon);
                drawable.setBounds(0, 0, context.getResources().getDimensionPixelSize(R.dimen.dp_16), context.getResources().getDimensionPixelSize(R.dimen.dp_10));
                holder.tvName.setCompoundDrawables(null,null,drawable,null);
            }else {
                holder.view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.tvName.setCompoundDrawables(null,null,null,null);
            }
            CollageListBean.DataBean item = (CollageListBean.DataBean) getItem(position);
            holder.tvName.setText(item.getZh_name());
            Glide.with(context).load(item.getIcon()).error(R.drawable.ic_launcher).into(holder.img_avater);
            return convertView;
        }
    }

    private class ViewHolder{
        private TextView tvName;
        private RoundImageView img_avater;
        private View view;
    }
}