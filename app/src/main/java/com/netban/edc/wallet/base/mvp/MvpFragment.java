package com.netban.edc.wallet.base.mvp;

import android.content.Context;

import com.netban.edc.wallet.base.BaseFragment;
import com.netban.edc.wallet.utils.TUtil;

/**
 * Created by Evan on 2018/8/10.
 */

public class MvpFragment<P extends BasePresenter> extends BaseFragment implements BaseContract.View<P> {
    protected P mpresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createPresenter();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void createPresenter() {
        mpresenter= TUtil.getT(this,0);
        if (mpresenter!=null) {
            mpresenter.attach(this, getContext());
            getLifecycle().addObserver(mpresenter);
        }
    }

    @Override
    public P getPresenter() {
        return mpresenter;
    }
}
