package com.netban.edc.wallet.base.rx;

import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonParseException;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.module.login.LoginActivity;
import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.NetUtils;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.NotifyDialog;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;

/**
 * Created by Evan on 2018/9/18.
 */

public class ErrorAction implements Action1<Throwable> {
    private final NotifyDialog notifyDialog;
    private final Context mcontext;

    public ErrorAction(NotifyDialog notifyDialog){
        this.notifyDialog=notifyDialog;
        this.mcontext=notifyDialog.getContext();
    }
    @Override
    public void call(Throwable throwable) {
        if (throwable==null){

            notifyDialog.setTitle(mcontext.getString(R.string.error_request));
            notifyDialog.setMsg(((HttpException) throwable).message());
            notifyDialog.setCanceledOnTouchOutside(false);
            notifyDialog.show();
            return;
        }

        if (throwable instanceof HttpException) {
            if (((HttpException) throwable).code() == 401) {
                skipLogin();
            }else if (((HttpException) throwable).code()==500){
                notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                notifyDialog.setMsg(((HttpException) throwable).message());
                notifyDialog.setCanceledOnTouchOutside(false);
                notifyDialog.show();
            }else if (((HttpException) throwable).code()==302){

                notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                notifyDialog.setMsg(((HttpException) throwable).message());
                notifyDialog.setCanceledOnTouchOutside(false);
                notifyDialog.show();
            }else if (((HttpException) throwable).code()==422){

                notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                notifyDialog.setMsg(((HttpException) throwable).message());
                notifyDialog.setCanceledOnTouchOutside(false);
                notifyDialog.show();
            }else if (((HttpException) throwable).code()==403){

                notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                notifyDialog.setMsg(((HttpException) throwable).message());
                notifyDialog.setCanceledOnTouchOutside(false);
                notifyDialog.show();
            }else{

                notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                notifyDialog.setMsg(mcontext.getString(R.string.error_network));
                notifyDialog.setCanceledOnTouchOutside(false);
                notifyDialog.show();
            }

        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException) {
            ToastUtils.showShortToast(mcontext,mcontext.getString(R.string.error_parse));

        } else if (throwable instanceof ConnectException) {
            ToastUtils.showShortToast(mcontext, mcontext.getString(R.string.error_connect_server));
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            ToastUtils.showShortToast(mcontext, mcontext.getString(R.string.error_validation));
        } else {
            if (!NetUtils.isConnected(mcontext)) {
                notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                notifyDialog.setMsg(mcontext.getString(R.string.error_network));
                notifyDialog.setCanceledOnTouchOutside(false);
                notifyDialog.show();
                return;
            }
            ToastUtils.showShortToast(mcontext, throwable.getMessage());
        }
    }

    private void  skipLogin(){
        Intent intent = new Intent(mcontext, LoginActivity.class);
        SharePreferencesHelper.getInstance(mcontext).saveSerializableObject("user", null);
        intent.putExtra("token",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityManager.getInstance().finishAllActivity();
        mcontext.startActivity(intent);
    }
}
