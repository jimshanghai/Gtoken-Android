package com.netban.edc.wallet.module.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.Constant;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.KeystoreBean;
import com.netban.edc.wallet.module.common.WebViewActivity;
import com.netban.edc.wallet.module.main.MainActivity;
import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.DateUtils;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.NotifyDialog;
import com.netban.edc.wallet.view.widget.NoneButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.netban.edc.wallet.base.Constant.Type.TYPE_PACT;
import static com.netban.edc.wallet.utils.FileUtils.getPath;
import static com.netban.edc.wallet.utils.FileUtils.getRealPathFromURI;

/**
 * Created by Evan on 2018/8/1.
 */

public class RegisterActivity extends MvpActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.spinner_code_photo)
    Spinner spinnerCodePhoto;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code_ver)
    EditText etCodeVer;
    @BindView(R.id.btn_code_ver)
    NoneButton btnCodeVer;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_ensure)
    EditText etPwdEnsure;
    @BindView(R.id.et_name_user)
    EditText etNameUser;
    @BindView(R.id.et_birth)
    EditText etBirth;
    @BindView(R.id.tv_man)
    TextView tvMan;
    @BindView(R.id.layout_man)
    LinearLayout layoutMan;
    @BindView(R.id.tv_woman)
    TextView tvWoman;
    @BindView(R.id.layout_woman)
    LinearLayout layoutWoman;
    @BindView(R.id.btn_register)
    NoneButton btnRegister;
    @BindView(R.id.cb_pact)
    CheckBox cbPact;
    @BindView(R.id.tv_pact)
    TextView tvPact;
    @BindView(R.id.layout_phone)
    LinearLayout layoutPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.layout_email)
    LinearLayout layoutEmail;
    @BindView(R.id.rb_phone)
    RadioButton rbPhone;
    @BindView(R.id.rb_email)
    RadioButton rbEmail;
    @BindView(R.id.tv_keystore_register)
    TextView tvKeystoreRegister;
    @BindView(R.id.tv_keystore)
    TextView tvKeystore;
    @BindView(R.id.layout_keystore)
    LinearLayout layoutKeystore;
    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.title_register)
    TextView titleRegister;
    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    private DatePicker datePicker;
    private int sex = 1;
    private int REQUEST_CODE = 110;
    private String fpath;
    private String ketstore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        ketstore = getIntent().getStringExtra("keystore");

        if (!TextUtils.isEmpty(ketstore)) {
            try {
                KeystoreBean keystoreBean = new Gson().fromJson(ketstore, KeystoreBean.class);
                mpresenter.setKeystore(ketstore);
                layoutKeystore.setVisibility(View.VISIBLE);
                tvKeystore.setText(keystoreBean.getAddress());
                titleRegister.setText(getString(R.string.title_keystore_register));
            } catch (JsonParseException e) {
                ToastUtils.showShortToast(this, getString(R.string.error_parse));
            } catch (Exception e) {
                ToastUtils.showShortToast(this, getString(R.string.error_parse));
            }
        }


        initPact();
        btnRegister.addReleView(etCodeVer, etPwd, etPwdEnsure, etNameUser, cbPact);
        //btnCodeVer.addReleView(etPhone);

        rbEmail.setBackgroundColor(Color.GRAY);

        rbPhone.setBackgroundColor(Color.parseColor("#5A86F4"));
        rbPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etEmail.setText(null);
                    rbEmail.setBackgroundColor(Color.GRAY);

                    rbPhone.setBackgroundColor(Color.parseColor("#5A86F4"));
                    layoutEmail.setVisibility(View.GONE);
                    layoutPhone.setVisibility(View.VISIBLE);

                    etCodeVer.setText(null);
                    etPwd.setText(null);
                    etPwdEnsure.setText(null);
                }
            }
        });

        rbEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPhone.setText(null);
                    layoutEmail.setVisibility(View.VISIBLE);
                    layoutPhone.setVisibility(View.GONE);
                    rbEmail.setBackgroundColor(Color.parseColor("#5A86F4"));
                    rbPhone.setBackgroundColor(Color.GRAY);
                    etCodeVer.setText(null);
                    etPwd.setText(null);
                    etPwdEnsure.setText(null);
                }
            }
        });


    }

    private void initPact() {
        if (PhoneSystemManager.getLanguage()[0].equals("en")) {
            tvPact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
//                    intent.putExtra("type", TYPE_PACT);
//                    startActivity(intent);
                    Intent intent= new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(Constant.Uri.URL_PACT);
                    intent.setData(content_url);
                    startActivity(intent);
                }
            });
            return;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(tvPact.getText().toString());
        tvPact.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
//                Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
//                intent.putExtra("type", TYPE_PACT);
//                startActivity(intent);
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(Constant.Uri.URL_PACT);
                intent.setData(content_url);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#333333"));
            }
        }, 7, 13, SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(Constant.Uri.URL_PACT);
                intent.setData(content_url);
                startActivity(intent);
//                Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
//                intent.putExtra("type", TYPE_PACT);
//                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#333333"));
            }
        }, 14, 20, SPAN_EXCLUSIVE_EXCLUSIVE);

        tvPact.setText(builder);

    }

    @OnClick({R.id.tv_keystore_register, R.id.btn_code_ver, R.id.layout_man, R.id.layout_woman, R.id.et_birth, R.id.img_back, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_man:
                layoutMan.setBackgroundColor(Color.parseColor("#8EBEFE"));
                layoutWoman.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Drawable man_draw = getResources().getDrawable(R.drawable.select_man);
                Drawable woman_draw = getResources().getDrawable(R.drawable.unselect_woman);
                man_draw.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_14));
                woman_draw.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_14));

                tvMan.setCompoundDrawables(man_draw, null, null, null);
                tvMan.setBackground(null);

                tvWoman.setCompoundDrawables(woman_draw, null, null, null);
                tvWoman.setBackground(null);
                sex = 1;
                break;
            case R.id.layout_woman:
                layoutMan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                layoutWoman.setBackgroundColor(Color.parseColor("#FE8CB4"));

                Drawable man_drawt = getResources().getDrawable(R.drawable.unselect_man);
                man_drawt.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_14));

                Drawable woman_drawt = getResources().getDrawable(R.drawable.select_woman);
                woman_drawt.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_14));

                tvMan.setCompoundDrawables(man_drawt, null, null, null);
                tvMan.setBackground(null);

                tvWoman.setCompoundDrawables(woman_drawt, null, null, null);
                tvWoman.setBackground(null);
                sex = 0;
                break;
            case R.id.et_birth:
                showBirthDia();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_code_ver:
                if (layoutPhone.getVisibility() == View.VISIBLE) {
                    String areas = ((String) spinnerCodePhoto.getSelectedItem());
                    String phone = etPhone.getText().toString();
                    mpresenter.smscodes(areas, phone);
                } else {
                    mpresenter.sendEmail(etEmail.getText().toString());
                }
                break;
            case R.id.tv_keystore_register:
                PhoneSystemManager.openSystenFileManager(this, REQUEST_CODE, "*/*");
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String name = etNameUser.getText().toString();
        String areas = ((String) spinnerCodePhoto.getSelectedItem());
        String phone = etPhone.getText().toString();
        String codever = etCodeVer.getText().toString();
        String pwd = etPwd.getText().toString();
        String pwdensure = etPwdEnsure.getText().toString();
        String email = etEmail.getText().toString().trim();
        if (layoutPhone.getVisibility() == View.VISIBLE) {
            mpresenter.register(name, areas, phone, pwd, pwdensure, String.valueOf(sex), codever);
        } else {
            mpresenter.registeremail(name, email, pwd, pwdensure, String.valueOf(sex), codever);

        }
    }

    /*创建日期选择器*/
    private void showBirthDia() {
        if (datePicker != null && datePicker.isShowing()) return;

        datePicker = new DatePicker(this);
        datePicker.setGravity(Gravity.BOTTOM);
        datePicker.setRangeStart(1900, 1, 1);
        datePicker.setRangeEnd(Integer.valueOf(DateUtils.getCurYear()), Integer.valueOf(DateUtils.getCurMonth()), Integer.valueOf(DateUtils.getCurDay()));
        datePicker.setSelectedItem(Integer.valueOf(DateUtils.getCurYear()), Integer.valueOf(DateUtils.getCurMonth()), Integer.valueOf(DateUtils.getCurDay()));
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                etBirth.setText(year + "-" + month + "-" + day);
            }
        });


        datePicker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                fpath = uri.getPath();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                fpath = getPath(this, uri);
            } else {//4.4以下下系统调用方法
                fpath = getRealPathFromURI(this, uri);
            }
            if (TextUtils.isEmpty(FileUtils.readFile(fpath))) {
                ToastUtils.showShortToast(this, getString(R.string.error_format_file));
                return;
            }
            String res = FileUtils.readKeystore(fpath);
            try {
                KeystoreBean keystoreBean = new Gson().fromJson(res, KeystoreBean.class);
                mpresenter.setKeystore(res);
                layoutKeystore.setVisibility(View.VISIBLE);
                tvKeystore.setText(keystoreBean.getAddress());
            } catch (JsonParseException e) {
                ToastUtils.showShortToast(this, getString(R.string.error_parse));
            } catch (Exception e) {
                ToastUtils.showShortToast(this, getString(R.string.error_parse));
            }
        }

    }

    @Override
    public void onSuccess() {
        notifyDialog.setTitle(getString(R.string.title_ensure_dia));
        notifyDialog.setMsg(getString(R.string.success_register));
        notifyDialog.show();
    }

    @Override
    public void onCall() {
        super.onCall();
        skipMain();
    }

    @Override
    public void onCancle() {
        super.onCancle();
        skipMain();
    }
    /**
     * 跳转主界面
     */
    private void skipMain() {
        startActivity(new Intent(this, MainActivity.class));
        ActivityManager.getInstance().finishAllActivity();
    }

    @Override
    public void onFail(String msg) {
        notifyDialog.setTitle(getString(R.string.title_ensure_dia));
        notifyDialog.setMsg(msg);
        notifyDialog.setListener(new NotifyDialog.OnCallListener() {
            @Override
            public void onCall() {

            }

            @Override
            public void onCancle() {

            }
        });
        notifyDialog.show();
    }

    @Override
    public void onGetCode() {
        ToastUtils.showShortToast(this, getString(R.string.success_send_register));
    }

    @Override
    public void onGetECode() {
        ToastUtils.showShortToast(this, getString(R.string.success_send_register));
    }
}
