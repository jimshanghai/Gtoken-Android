package com.netban.edc.wallet.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.ToastUtils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.netban.edc.wallet.base.Constant.Code.CODE_REQUEST_PERMISSION;

/**
 * Created by Evan on 2018/7/31.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity pactivity;
    private boolean isVisible;
    private User.DataBean user;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pactivity= ((Activity) context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected abstract void init() ;
    protected abstract void loadData();

    /**
     * true:nothing
     * @param permissions
     * @return
     */
    protected Boolean checkPermission(String... permissions){
        for (int i=0;i<permissions.length;i++){
            String permission=permissions[i];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int result = getContext().checkSelfPermission(permission);
                if (result!=PERMISSION_GRANTED){
                    if (!shouldShowRequestPermissionRationale(permission)){
                        String[] pv = getResources().getStringArray(R.array.persissions_value);
                        ToastUtils.showLongToast(getContext(),pv[i]);
                    }
                    requestPermissions(permissions,CODE_REQUEST_PERMISSION);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==CODE_REQUEST_PERMISSION){
            for (int i=0;i<grantResults.length;i++){
                if (grantResults[i]!=PERMISSION_GRANTED){
                    Toast.makeText(getContext(),"权限申请失败",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"权限申请成功",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            loadData();
            isVisible=true;
        }else{
            isVisible=false;
        }
    }

    public User.DataBean getUser() {
        if (user==null)
            user = ((User.DataBean) SharePreferencesHelper.getInstance(pactivity).getSerializableObject(pactivity, "user"));
        return user;
    }
    public User.DataBean getFUser() {
        user = ((User.DataBean) SharePreferencesHelper.getInstance(pactivity).getSerializableObject(pactivity, "user"));
        return user;
    }
    public void setUser(User.DataBean user) {
        this.user = user;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
