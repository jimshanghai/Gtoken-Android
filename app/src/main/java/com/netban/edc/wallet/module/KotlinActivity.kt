package com.netban.edc.wallet.module

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.netban.edc.wallet.R
import com.netban.edc.wallet.base.BaseActivity
import com.netban.edc.wallet.base.mvp.BasePresenter
import rx.Observable

class KotlinActivity : BaseActivity() {
    override fun init() {


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
    }


    class KotlinPersenter:BasePresenter<Any,Any>(){

    }
}
