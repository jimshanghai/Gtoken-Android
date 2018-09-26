package com.netban.edc.wallet.module.main.personal;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpFragment;
import com.netban.edc.wallet.module.contacts.ContactsActivity;
import com.netban.edc.wallet.module.groom.GroomActivity;
import com.netban.edc.wallet.module.keystore.manager.KeyStoreShowActivity;
import com.netban.edc.wallet.module.keystore.repwd.KeyPwdResetActivity;
import com.netban.edc.wallet.module.personal.PersonalActivity;
import com.netban.edc.wallet.module.pwdreset.PwdResetActivity;
import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.view.dialog.NotifyDialog;
import com.netban.edc.wallet.view.widget.RingImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Evan on 2018/8/10.
 */

public class PersonalFragment extends MvpFragment<PersonalPresenter> implements PersonalContract.View, NotifyDialog.OnCallListener {

    @BindView(R.id.img_avater)
    RingImageView imgAvater;
    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.img_contract)
    ImageView imgContract;
    @BindView(R.id.layout_contacts)
    RelativeLayout layoutContacts;
    @BindView(R.id.img_person)
    ImageView imgPerson;
    @BindView(R.id.layout_personal)
    RelativeLayout layoutPersonal;
    @BindView(R.id.img_pwd_gtoken)
    ImageView imgPwdGtoken;
    @BindView(R.id.layout_pwd_reset)
    RelativeLayout layoutPwdReset;
    @BindView(R.id.img_ys)
    ImageView imgYs;
    @BindView(R.id.layout_manager_key)
    RelativeLayout layoutManagerKey;
    @BindView(R.id.img_groom)
    ImageView imgGroom;
    @BindView(R.id.layout_groom)
    RelativeLayout layoutGroom;
    @BindView(R.id.img_exit)
    ImageView imgExit;
    @BindView(R.id.layout_exit)
    RelativeLayout layoutExit;
    Unbinder unbinder;
    private NotifyDialog notifyDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        StatusBarUtil.setColor(getActivity(), Color.parseColor("#FFFFFF"));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_personal, null);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init() {
        super.init();
        tvNumber.setText(getString(R.string.number_person) + getUser().getNumbers());
        Glide.with(pactivity).load(getFUser().getAvatar()).error(R.drawable.ic_launcher).skipMemoryCache(true).into(imgAvater);
        tvNameUser.setText(getFUser().getName());
//        if (getUser().getType().equals("1")){
//            layoutManagerKey.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(pactivity).load(getFUser().getAvatar()).error(R.drawable.ic_launcher).skipMemoryCache(true).into(imgAvater);
        tvNameUser.setText(getFUser().getName());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout_groom,R.id.layout_exit, R.id.layout_manager_key, R.id.layout_pwd_reset, R.id.layout_personal, R.id.layout_contacts, R.id.img_avater})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.layout_groom:
                startActivity(new Intent(getContext(), GroomActivity.class));
                break;
            case R.id.layout_manager_key:
                if (getUser().getType().equals("2")) {
                    startActivity(new Intent(getContext(), KeyStoreShowActivity.class));
                } else {
                    startActivity(new Intent(getContext(), KeyPwdResetActivity.class));
                }

                break;
            case R.id.layout_pwd_reset:

                if (TextUtils.isEmpty(getUser().getEmail()) && TextUtils.isEmpty(getUser().getMobile())) {
                    notifyDialog = new NotifyDialog(getContext());
                    notifyDialog.setTitle(getString(R.string.title_bind_dia));
                    notifyDialog.setMsg(getString(R.string.msg_bind_dia));
                    notifyDialog.setListener(this);
                    notifyDialog.show();
                    break;
                }
                startActivity(new Intent(getContext(), PwdResetActivity.class));
                break;
//            case R.id.layout_repwd_trade:
//                startActivity(new Intent(getContext(), TradePwdResetActivity.class));
//                break;
            case R.id.layout_personal:
                startActivity(new Intent(getContext(), PersonalActivity.class));
                break;
            case R.id.layout_contacts:
                startActivity(new Intent(getContext(), ContactsActivity.class));
                break;
            case R.id.img_avater:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(getContext(), PersonalActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(), imgAvater, "imgavatar").toBundle());
                } else {
                    startActivity(new Intent(getContext(), PersonalActivity.class));
                }
                break;
            case R.id.layout_exit:
                notifyDialog = new NotifyDialog(getContext());
                notifyDialog.setTitle(getString(R.string.title_ensure_dia));
                notifyDialog.setMsg(getString(R.string.msg_exit_system));
                notifyDialog.setOk(getString(R.string.exit_system));
                notifyDialog.setListener(new NotifyDialog.OnCallListener() {
                    @Override
                    public void onCall() {
                        SharePreferencesHelper.getInstance(getContext()).saveSerializableObject("user", null);

                        ActivityManager.getInstance().finishAllActivity();
                    }

                    @Override
                    public void onCancle() {

                    }
                });
                notifyDialog.show();


                break;
        }
    }

    @Override
    public void onCall() {
        startActivity(new Intent(getContext(), PersonalActivity.class));

    }

    @Override
    public void onCancle() {

    }
}
