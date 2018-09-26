package com.netban.edc.wallet.module.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.RequestListBean;
import com.netban.edc.wallet.module.login.LoginActivity;
import com.netban.edc.wallet.module.main.MainActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class StartOverActivity extends BaseActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what){
                case 0:
                    startActivity(new Intent(StartOverActivity.this, LoginActivity.class));
                    finish();
                    break;
                case 1:
                    startActivity(new Intent(StartOverActivity.this, MainActivity.class));
                    finish();
                    break;
                default:
                    startActivity(new Intent(StartOverActivity.this, LoginActivity.class));
                    finish();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_over);
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getUser()==null) {
                    handler.sendEmptyMessage(0);
                    return;
                }
                String token = getUser().getToken();

                if (TextUtils.isEmpty(token)){
                    handler.sendEmptyMessage(0);
                }else{
                    handler.sendEmptyMessage(1);
                }
            }
        },3000);
    }

    @Override
    protected void init() {

    }
}
