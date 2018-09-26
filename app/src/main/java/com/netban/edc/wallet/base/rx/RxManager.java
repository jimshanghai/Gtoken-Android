package com.netban.edc.wallet.base.rx;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class RxManager {

   
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者



    public void add(Subscription m) {
        mCompositeSubscription.add(m);
    }

    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消订阅
    }


}
