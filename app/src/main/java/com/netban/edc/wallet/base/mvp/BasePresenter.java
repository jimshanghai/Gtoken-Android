package com.netban.edc.wallet.base.mvp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.rx.RxManager;
import com.netban.edc.wallet.bean.BaseBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.module.common.StartOverActivity;
import com.netban.edc.wallet.module.keystore.ValiDateKeystoreActivity;
import com.netban.edc.wallet.module.login.LoginActivity;
import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.NetUtils;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.TUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.NotifyDialog;
import com.netban.edc.wallet.view.dialog.ProgressDialog;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Evan on 2018/7/31.
 */

public class BasePresenter<M,V> implements BaseContract.Presenter<M,V>,NotifyDialog.OnCallListener {
    protected M model;
    protected V mview;
    protected RxManager rxManager;
    protected Context mcontext;
    protected ProgressDialog progressDialog;
    protected NotifyDialog notifyDialog;
    private boolean isToken;
    @Override
    public void attach(V view,Context context) {
        mview=view;
        mcontext=context;
        model= TUtil.getT(this,0);
        rxManager=new RxManager();
        progressDialog = new ProgressDialog(mcontext,R.style.translationTheme);
        notifyDialog=new NotifyDialog(context);
        notifyDialog.setListener(this);

        if (model instanceof BaseModel) {
            User.DataBean user = (User.DataBean) SharePreferencesHelper.getInstance(context).getSerializableObject(context, "user");
            if (user==null)return;
            String token = user.getToken();
            ((BaseModel) model).setAuthorization(token);
        }

    }
    public void dismiss(){
        if (progressDialog!=null) {
            progressDialog.dismiss();
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(LifecycleOwner owner) {

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(LifecycleOwner owner) {

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory(LifecycleOwner owner) {
        if (progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
        model=null;
        mview=null;
        rxManager.clear();
    }

    @Override
    public void onCall() {
        if (isToken) {
            notifyDialog.dismiss();
            Intent intent = new Intent(mcontext, StartOverActivity.class);
            SharePreferencesHelper.getInstance(mcontext).saveSerializableObject("user", null);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityManager.getInstance().finishAllActivity();
            mcontext.startActivity(intent);
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
    @Override
    public void onCancle() {

    }

    public class OnNextAction<T  extends BaseBean> implements Action1<T>{

        @Override
        public void call(T bean) {


               if (bean.getCode()!=200){
                   if (progressDialog!=null){
                       progressDialog.dismiss();
                   }
                   if (bean.getCode()==401){
                       isToken=true;
                       notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                       notifyDialog.setMsg(mcontext.getString(R.string.msg_token_dia));
                       notifyDialog.setCanceledOnTouchOutside(false);
                       notifyDialog.show();
                   }
               }
        }
    }
    public class OnErrorAction implements Action1<Throwable>{
        @Override
        public void call(Throwable throwable) {
            if (throwable==null){
                isToken=false;
                notifyDialog.setTitle(mcontext.getString(R.string.error_request));
                notifyDialog.setMsg(((HttpException) throwable).message());
                notifyDialog.setCanceledOnTouchOutside(false);
                notifyDialog.show();
             return;
            }
            if (progressDialog != null)
                progressDialog.dismiss();
            if (throwable instanceof HttpException) {
                if (((HttpException) throwable).code() == 401) {
                    isToken=true;
                    notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                    notifyDialog.setMsg(mcontext.getString(R.string.msg_token_dia));
                    notifyDialog.setCanceledOnTouchOutside(false);
                    notifyDialog.show();
                }else if (((HttpException) throwable).code()==500){
                    isToken=false;
                    notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                    notifyDialog.setMsg(((HttpException) throwable).message());
                    notifyDialog.setCanceledOnTouchOutside(false);
                    notifyDialog.show();
                }else if (((HttpException) throwable).code()==302){
                    isToken=false;
                    notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                    notifyDialog.setMsg(((HttpException) throwable).message());
                    notifyDialog.setCanceledOnTouchOutside(false);
                    notifyDialog.show();
                }else if (((HttpException) throwable).code()==422){
                    isToken=false;
                    notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                    notifyDialog.setMsg(((HttpException) throwable).message());
                    notifyDialog.setCanceledOnTouchOutside(false);
                    notifyDialog.show();
                }else if (((HttpException) throwable).code()==403){
                    isToken=false;
                    notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
                    notifyDialog.setMsg(((HttpException) throwable).message());
                    notifyDialog.setCanceledOnTouchOutside(false);
                    notifyDialog.show();
                }else{
                    isToken=false;
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
//            if (progressDialog!=null){
//                progressDialog.dismiss();
//            }
//            isToken=false;
//            if (throwable instanceof HttpException) {
//                if (((HttpException) throwable).code()==401){
//                    notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
//                    notifyDialog.setMsg(mcontext.getString(R.string.msg_token_dia));
//                    notifyDialog.setCanceledOnTouchOutside(false);
//                    notifyDialog.show();
//                    isToken=true;
//                    return;
//                }
////                ToastUtils.showShortToast(mcontext, mcontext.getString(R.string.error_network));
//                notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
//                notifyDialog.setMsg(mcontext.getString(R.string.error_network));
//                notifyDialog.setCanceledOnTouchOutside(false);
//                notifyDialog.show();
//            } else if (throwable instanceof JsonParseException
//                    || throwable instanceof JSONException) {
//                ToastUtils.showShortToast(mcontext, mcontext.getString(R.string.error_parse));
//
//            } else if (throwable instanceof ConnectException) {
//                ToastUtils.showShortToast(mcontext, mcontext.getString(R.string.error_connect_server));
//            } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
//                ToastUtils.showShortToast(mcontext, mcontext.getString(R.string.error_validation));
//            } else {
//                if (!NetUtils.isConnected(mcontext)){
//                    notifyDialog.setTitle(mcontext.getString(R.string.title_notify_camera_home));
//                    notifyDialog.setMsg(mcontext.getString(R.string.error_network));
//                    notifyDialog.setCanceledOnTouchOutside(false);
//                    notifyDialog.show();
//                    return;
//                }
////                ToastUtils.showShortToast(mcontext, throwable.getMessage());
//            }


        }
    }


    public class DoOnSubscribe implements Action0 {
        @Override
        public void call() {
            if (progressDialog!=null){
                progressDialog.show();
            }
        }
    }

    public class CompletedAction implements Action0 {

        @Override
        public void call() {
                    if (progressDialog!=null){
                        progressDialog.dismiss();
                    }
        }
    }
}
