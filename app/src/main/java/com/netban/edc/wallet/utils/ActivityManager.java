package com.netban.edc.wallet.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.Stack;

/**
 * Created by CZY on 2017/10/19.
 */
public class ActivityManager {
    private Stack<? super Activity> stack;
    private static ActivityManager manager;
    private ActivityManager(){

    }
    public static synchronized ActivityManager getInstance(){
        if (manager==null){
            manager=new ActivityManager();
        }
        return manager;
    }
    //进栈
    public void pushActivity(Activity activity){
        if(stack==null){
            stack=new Stack<>();
        }
        stack.push(activity);
    }
    //出栈
    public boolean popActivity(Activity activity){
        if(stack==null){
            return false;
        }
        if (stack.empty()){
            return false;
        }
        if (activity==null)
            return false;
        return stack.remove(activity);
    }
    /**
     * 结束指定activity
     *
     * @param activity
     */
    public void endActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            stack.remove(activity);
            activity = null;
        }
    }
    /**
     * 获得当前的activity(即最上层)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!stack.empty())
            activity = (Activity) stack.peek();
        return activity;
    }

    /**
     * 弹出除cls外的所有activity
     *
     * @param cls
     */
    public void popAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 结束除cls之外的所有activity,执行结果都会清空Stack
     *
     * @param cls
     */
    public void finishAllActivityExceptOne(Class<? extends Activity> cls) {

        while (!stack.empty()) {
            Activity activity = currentActivity();
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
            } else {
                if (stack.size()==1){
                    currentActivity().startService(new Intent("android.intent.action.REPLACE_ICON"));
                }
                endActivity(activity);
            }
        }

    }
    /**
     * 结束所有activity
     */
    public void finishAllActivityExceptTop() {
        if (stack.empty())return;

        for (int i=stack.size()-1;i>0;i--){
            endActivity((Activity) stack.get(i));
        }
//        while (!stack.empty()) {
//            Activity activity = currentActivity();
//            endActivity(activity);
//        }
    }
    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        while (!stack.empty()) {
            Activity activity = currentActivity();
            endActivity(activity);
        }
    }

}
