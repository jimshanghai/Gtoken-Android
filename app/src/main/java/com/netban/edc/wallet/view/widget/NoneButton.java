package com.netban.edc.wallet.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.netban.edc.wallet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CZY on 2017/10/18.
 */
public class NoneButton extends Button {
    private List<? super View> mvlist;
    private View snView;
    public NoneButton(Context context) {
        super(context);
    }

    public NoneButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoneButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //添加关联控件
    public void addReleView(View view){
        if (mvlist==null){
            mvlist=new ArrayList<>();
        }
        mvlist.add(view);
    }
    public void addReleView(View... view){
        if (mvlist==null){
            mvlist=new ArrayList<>();
        }
        for (int i=0;i<view.length;i++){
            mvlist.add(view[i]);
        }
    }
    public void removeReleView(View view){
        if (mvlist==null)return;
        mvlist.remove(view);
    }
    @Override
    public boolean performClick() {
        if (mvlist!=null&&mvlist.size()>0) {
            for (int i = 0; i < mvlist.size(); i++) {
                if (checkNone((mvlist.get(i)))) {
                    return true;
                }
            }
        }
        return super.performClick();
    }

    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }
    public View getSnView() {
        return snView;
    }

    public void setSnView(View snView) {
        this.snView = snView;
    }


    //判null
    private   boolean checkNone(Object t){
                if (t instanceof EditText){
                    Editable text = ((EditText) t).getText();
                    if (TextUtils.isEmpty(text.toString())){
                        ((EditText) t).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) t).requestFocus();
                            }
                        },500);
                        ((EditText) t).setHint(getContext().getString(R.string.required));
                        ((EditText) t).setHintTextColor(Color.GRAY);
                        Snackbar snackbar = Snackbar.make(getSnView()==null?this:getSnView(), getContext().getString(R.string.notify_required), Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.WHITE);
                        setSnackbarMessageTextColor(snackbar,Color.BLACK);
                        snackbar.show();
                        return true;
                    }
                }else if (t instanceof CheckBox){
                    CheckBox checkBox = (CheckBox) t;
                    if (!checkBox.isChecked()){
                        Snackbar snackbar = Snackbar.make(getSnView()==null?this:getSnView(), getContext().getString(R.string.checked_required), Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.WHITE);
                        setSnackbarMessageTextColor(snackbar,Color.BLACK);
                        snackbar.show();
                        return true;
                    }
                } else if (t instanceof ImageView){
                    ImageView imageView = (ImageView) t;
                    if (imageView.getDrawable()==null){
                        Snackbar snackbar = Snackbar.make(getSnView()==null?this:getSnView(),  getContext().getString(R.string.notify_required), Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.WHITE);
                        setSnackbarMessageTextColor(snackbar,Color.BLACK);
                        snackbar.show();
                        return true;
                    }
                }else if (t instanceof TextView){
                    CharSequence text = ((TextView) t).getText();
                    if (TextUtils.isEmpty(text.toString())){
                        ((TextView) t).setVisibility(VISIBLE);
                        ((TextView) t).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) t).requestFocus();
                            }
                        },500);
                        ((TextView) t).setHint(getContext().getString(R.string.required));
                        ((TextView) t).setHintTextColor(Color.GRAY);
                        Snackbar snackbar = Snackbar.make(getSnView()==null?this:getSnView(),  getContext().getString(R.string.notify_required), Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.WHITE);
                        setSnackbarMessageTextColor(snackbar,Color.BLACK);
                        snackbar.show();
                        return true;
                    }
                }

        return false;
    }
}
