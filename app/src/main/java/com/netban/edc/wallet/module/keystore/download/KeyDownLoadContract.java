package com.netban.edc.wallet.module.keystore.download;

/**
 * Created by Evan on 2018/8/13.
 */

public interface KeyDownLoadContract {
    interface View{
        void downSuccess();
        void downFail(String msg);
    }
    interface Presenter{
        void down();
    }

    interface Model{

    }
}
