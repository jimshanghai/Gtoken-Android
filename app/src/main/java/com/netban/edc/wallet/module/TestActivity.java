package com.netban.edc.wallet.module;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.view.widget.DragListItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/8/1.
 */

public class TestActivity extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    ListView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

    }

    class Adapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = new DragListItem(parent.getContext());

            return view;
        }
    }
}
