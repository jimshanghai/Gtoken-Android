package com.netban.edc.wallet.module.personal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.api.UploadManagerUtils;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.TokenBean;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.RoundImageView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/13.
 */

public class PersonalActivity extends BaseActivity {

    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.img_avater)
    RoundImageView imgAvater;
    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_phone_user)
    TextView tvPhoneUser;
    @BindView(R.id.layout_avatar)
    RelativeLayout layoutAvatar;
    @BindView(R.id.layout_name)
    RelativeLayout layoutName;
    @BindView(R.id.layout_phone)
    RelativeLayout layoutPhone;
    @BindView(R.id.root_view)
    LinearLayout rootView;
    @BindView(R.id.layout_exit)
    RelativeLayout layoutExit;
    @BindView(R.id.tv_address_gongga)
    TextView tvAddressGongga;
    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.tv_email_user)
    TextView tvEmailUser;
    @BindView(R.id.layout_email)
    RelativeLayout layoutEmail;
    @BindView(R.id.tv_number_user)
    TextView tvNumberUser;
    @BindView(R.id.layout_number)
    RelativeLayout layoutNumber;
    private MaterialDialog.Builder builder;
    private int REQUEST_CODE_CHOOSE = 100;
    private List<Uri> mSelected;
    private int REQUEST_CROP = 101;
    private String avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvNameUser.setText(getFUser().getName());
        tvPhoneUser.setText(getFUser().getMobile());
        tvAddressGongga.setText(getFUser().getPrivate_address());
        tvEmailUser.setText(getFUser().getEmail());
        tvNumberUser.setText(getFUser().getNumbers());
        Glide.with(this).load(getFUser().getAvatar()).into(imgAvater);
    }

    @OnClick({R.id.tv_phone_user,R.id.tv_email_user,R.id.layout_email, R.id.layout_avatar, R.id.layout_name, R.id.layout_phone, R.id.layout_exit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.layout_avatar:
                Matisse.from(this)
                        .choose(MimeType.allOf()) // 选择 mime 的类型
                        .countable(true)
                        .theme(R.style.Matisse_Dracula)
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, "com.netban.edc.wallet.fileprovider"))
                        .maxSelectable(1) // 图片选择的最多数量
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
                break;
            case R.id.layout_name:
                startActivity(new Intent(this, NameSetActivity.class));
                break;
            case R.id.tv_phone_user:
            case R.id.layout_phone:
                Intent intent = new Intent(this, PhoneAmendActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.layout_exit:
                SharePreferencesHelper.getInstance(this).saveSerializableObject("user", null);
                ActivityManager.getInstance().finishAllActivity();
                break;
            case R.id.tv_email_user:
            case R.id.layout_email:
                Intent intent1 = new Intent(this, EmailAmendActivity.class);
                intent1.putExtra("type", 2);
                startActivity(intent1);
                break;
                default:
                    finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if (mSelected == null || mSelected.size() <= 0)
                return;
            File file = FileUtils.saveAvatar(this, mSelected.get(0));
            if (!file.exists()) {
                ToastUtils.showShortToast(this, getString(R.string.fail_save_data_personal));
            }
            if (file.length() <= 0) {
                ToastUtils.showShortToast(this, getString(R.string.fail_save_data_personal));
            }
            NetClient.getInstance().getNetApi().getQNToken().subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread())
                    .flatMap(new Func1<TokenBean, Observable<RequestBean>>() {
                        @Override
                        public Observable<RequestBean> call(TokenBean tokenBean) {
                            avatar = UploadManagerUtils.getInstance().uploadAvatar(PersonalActivity.this, tokenBean.getUptoken(), file, getUser().getNumbers());
                            if (TextUtils.isEmpty(avatar))
                                return null;
                            return NetClient.getInstance().getNetApi().updateUser(getUser().getToken(), "", avatar, "", "");

                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<RequestBean>() {
                        @Override
                        public void call(RequestBean bean) {
                            if (bean == null) {
                                ToastUtils.showShortToast(PersonalActivity.this, getString(R.string.fail_save_data_personal));
                                return;
                            }
                            if (bean.getCode() == 200) {
                                ToastUtils.showShortToast(PersonalActivity.this, getString(R.string.success_save_data_personal));
                                if (!TextUtils.isEmpty(avatar) && !avatar.equals(getUser().getAvatar())) {
                                    User.DataBean user = getUser();
                                    user.setAvatar(avatar);
                                    SharePreferencesHelper.getInstance(PersonalActivity.this).saveSerializableObject("user", user);

                                    Glide.with(PersonalActivity.this).load(avatar).into(imgAvater);
                                }
                            } else {
                                ToastUtils.showShortToast(PersonalActivity.this, getString(R.string.fail_save_data_personal));

                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtils.showShortToast(PersonalActivity.this, getString(R.string.fail_save_data_personal));
                        }
                    });
        }
    }


}
