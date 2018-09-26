package com.netban.edc.wallet.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netban.edc.wallet.R;

/**
 * Created by Evan on 2018/8/17.
 */

public class NotifyDialog extends AlertDialog {

    private TextView tv_title;
    private TextView tv_msg;
    private Button btn_ensure;

    private String title;
    private String msg;
    private String ok;

    private OnCallListener listener;
    private ImageView img_cancle;

    public NotifyDialog(@NonNull Context context) {
        super(context);
    }

    public NotifyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public NotifyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setListener(OnCallListener listener) {
        this.listener = listener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (tv_title!=null){
            tv_title.setText(title);
        }

    }
    @Deprecated
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     */
    @Deprecated
    public void setMsg(String msg) {
        this.msg = msg;

        if (tv_msg!=null){
            tv_msg.setText(msg);
        }

    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
        if (btn_ensure!=null){
            btn_ensure.setText(ok);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notify_dialog);
        tv_title = ((TextView) findViewById(R.id.tv_title));
        tv_msg = ((TextView) findViewById(R.id.tv_msg));
        img_cancle=(ImageView)findViewById(R.id.img_cancle);
        btn_ensure = ((Button) findViewById(R.id.btn_ensure));
        btn_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onCall();
                }
                dismiss();
            }
        });
        img_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onCancle();
                }
                dismiss();
            }
        });
        ok=TextUtils.isEmpty(ok)?btn_ensure.getText().toString():ok;
        tv_msg.setText(msg);
        tv_title.setText(title);
        btn_ensure.setText(ok);
    }

    @Override
    public void show() {
        if (isShowing())dismiss();
        super.show();
        if (TextUtils.isEmpty(tv_msg.getText().toString())||TextUtils.isEmpty(tv_title.getText().toString())||TextUtils.isEmpty(btn_ensure.getText().toString())){
            throw new NullPointerException("NullPointerException");
        }
        int width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width=width*4/5;
        getWindow().setAttributes(attributes);
    }

    public interface OnCallListener{
        void onCall();
        void onCancle();
    }
}
