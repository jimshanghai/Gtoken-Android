package com.netban.edc.wallet.base;

import android.app.Instrumentation;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.NotifyDialog;

import java.util.Locale;

import butterknife.ButterKnife;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.netban.edc.wallet.base.Constant.Code.CODE_REQUEST_PERMISSION;

/**
 * Created by Evan on 2018/7/31.
 */

public abstract class BaseActivity extends AppCompatActivity implements NotifyDialog.OnCallListener {

    private User.DataBean user;
    protected NotifyDialog notifyDialog;
    protected String language;
    private View back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setColor(this, Color.WHITE);
    }
    @Override
    public void setContentView(int layoutResID) {
        String[] language = PhoneSystemManager.getLanguage();
        PhoneSystemManager.initLocaleLanguage(this, language[0],language[1]);
        this.language = language[0];
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        try {
            back = findViewById(R.id.img_back);
            if (back!=null){
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread () {
                            public void run () {
                                try {
                                    Instrumentation inst= new Instrumentation();
                                    inst.sendKeyDownUpSync(KeyEvent. KEYCODE_BACK);
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                });
            }

        }catch (NullPointerException e){
            Log.e(getLocalClassName(),e.getMessage());
        }
        notifyDialog=new NotifyDialog(this);
        notifyDialog.setListener(this);

        init();
    }

    /**
     * 初始化的操作
     * 如数据加载，权限申请等。
     */
    protected abstract void init();

    protected void checkPermission(String[] permissions){
        for (int i=0;i<permissions.length;i++){
            String permission=permissions[i];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int result = checkSelfPermission(permission);
                if (result!=PERMISSION_GRANTED){
                    if (!shouldShowRequestPermissionRationale(permission)){
                        String[] pv = getResources().getStringArray(R.array.persissions_value);
                        ToastUtils.showLongToast(this,pv[i]);
                    }
                    requestPermissions(permissions,CODE_REQUEST_PERMISSION);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==CODE_REQUEST_PERMISSION){
            for (int i=0;i<grantResults.length;i++){
                if (grantResults[i]!=PERMISSION_GRANTED){
                    Toast.makeText(this,"权限申请失败",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"权限申请成功",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
    public void showToast(String msg){
        ToastUtils.showShortToast(this,msg);
    }
    public User.DataBean getUser() {
        if (user==null)
            user = ((User.DataBean) SharePreferencesHelper.getInstance(this).getSerializableObject(this, "user"));
        return user;
    }
    public User.DataBean getFUser() {
        user = ((User.DataBean) SharePreferencesHelper.getInstance(this).getSerializableObject(this, "user"));
        return user;
    }

    public void setUser(User.DataBean user) {
        this.user = user;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCall() {

    }

    @Override
    public void onCancle() {

    }
}
