package com.netban.edc.wallet.module.keystore.download;

import com.netban.edc.wallet.base.mvp.BasePresenter;

/**
 * Created by Evan on 2018/8/13.
 */

public class KeyDownLoadPresenter extends BasePresenter<KeyDownLoadModel,KeyDownLoadContract.View> implements KeyDownLoadContract.Presenter {
    @Override
    public void down() {
        mview.downSuccess();
    }
}
