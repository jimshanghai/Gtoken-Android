package com.netban.edc.wallet.base.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;

/**
 * Created by Evan on 2018/7/31.
 */

public interface BaseContract {
    interface View<P>{
        void createPresenter();
        P getPresenter();
    }
    interface Presenter<M,V> extends LifecycleObserver{
       void attach(V view, Context context);
    }

    interface Model{

    }


}
