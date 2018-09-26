package com.netban.edc.wallet.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.utils.TUtil;

/**
 * Created by Evan on 2018/7/31.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity implements BaseContract.View<P>{
    protected P mpresenter;

    @Override
    public void createPresenter() {
        mpresenter= TUtil.getT(this,0);
        mpresenter.attach(this,this);
        getLifecycle().addObserver(mpresenter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        createPresenter();
        super.onCreate(savedInstanceState);
    }


    @Override
    public P getPresenter() {
        return mpresenter;
    }
}
