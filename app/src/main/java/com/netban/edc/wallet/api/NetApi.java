package com.netban.edc.wallet.api;


import com.netban.edc.wallet.bean.ApkBean;
import com.netban.edc.wallet.bean.BindKeyStoreBean;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.bean.GroomBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;
import com.netban.edc.wallet.bean.ToUser;
import com.netban.edc.wallet.bean.TokenBean;
import com.netban.edc.wallet.bean.TradeListBean;
import com.netban.edc.wallet.bean.TradeOutBean;
import com.netban.edc.wallet.bean.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Evan on 2018/8/1.
 *
 *     @Headers({"Accept:application/json"})
 *     @Header("Authorization")
 *
 */

public interface NetApi {

    /**
     * 注册接口
     * @param name 姓名
     * @param areas 手机区号 0086
     * @param mobile 手机号
     * @param password 密码 最少六位
     * @param password_confirmation 确认密码 最少六位
     * @param sex 性别
     * @param verification 验证码 	性别1:男;0:女
     * @return
     */
    @POST("api/register")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean<User.DataBean>> register(@Field("name")String name,@Field("areas")String areas,@Field("mobile")String mobile,@Field("password")String password,@Field("password_confirmation")String password_confirmation,@Field("sex")String sex,@Field("verification")String verification,@Field("keystore")String keystore);


    /**
     * 注册接口
     * @param name 姓名
     * @param areas 手机区号 0086
     * @param mobile 手机号
     * @param password 密码 最少六位
     * @param password_confirmation 确认密码 最少六位
     * @param sex 性别
     * @param verification 验证码 	性别1:男;0:女
     * @return
     */
    @POST("api/register")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean<User.DataBean>> registeremail(@Field("name")String name,@Field("email")String email,@Field("password")String password,@Field("password_confirmation")String password_confirmation,@Field("sex")String sex,@Field("verification")String verification,@Field("keystore")String keystore);





    /**
     * 验证码获取
     * @param areas
     * @param mobile
     * @return
     */
    @POST("api/smscodes")
    @FormUrlEncoded
    Observable<RequestBean> smscodes(@Field("area")String area,@Field("mobile")String mobile,@Field("type")String type);

    /**
     * 邮箱验证码获取
     * @param areas
     * @param mobile
     * @return
     */
    @POST("api/sendEmail")
    @FormUrlEncoded
    Observable<RequestBean> sendEmail(@Field("email")String email,@Field("type")String type);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("api/login")
    @FormUrlEncoded
    Observable<RequestBean<User.DataBean>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 用户联系人
     * @param Authorization
     * @param contract
     * @return
     */
    @Headers({"Accept:application/json"})
    @POST("api/getUserContact")
    @FormUrlEncoded
    Observable<RequestListBean<ContractsListBean.DataBean>> getUserContact(@Header("Authorization")String Authorization, @Field("contract")String contract );


    @POST("api/getUserLately")
    @Headers({"Accept:application/json"})
    @FormUrlEncoded
    Observable<RequestListBean<ContractsListBean.DataBean>> getUserLately(@Header("Authorization")String Authorization, @Field("contract")String contract );

    /**
     * 首页列表
     * @param Authorization
     * @return
     */
    @Headers({"Accept:application/json"})
    @POST("api/walletList")
    Observable<RequestListBean<CollageListBean.DataBean>> walletList(@Header("Authorization")String Authorization);

    /**
     * 明细
     * @param Authorization
     * @param token
     * @param page
     * @param limit
     * @return
     */

    @POST("api/transactions_list")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<TradeListBean> transactions_list(@Header("Authorization")String Authorization, @Field("token")String token, @Field("page") String page, @Field("limit")String limit);

//    @POST("api/edcTransferTccountList")
//    @FormUrlEncoded
//    @Headers({"Accept:application/json"})
//    Observable<RequestBean> edcTransferTccountList(@Header("Authorization")String Authorization);


    /**
     * 添加联系人
     * @param Authorization
     * @param numbers
     * @param name
     * @return
     */
    @POST("api/addtUserContact")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> addtUserContact(@Header("Authorization")String Authorization,@Field("numbers")String numbers,@Field("name")String  name);

    /**
     * 判断用户是否可以转账
     * @param Authorization
     * @param val
     * @param numbers
     * @param contract
     * @return
     */
    @POST("api/isTransferAccounts")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccounts(@Header("Authorization")String Authorization, @Field("val")double val, @Field("numbers")String numbers, @Field("contract")String contract,@Field("keystorepsd")String keystorepsd);

    /**
     * 判断用户是否可以转账 地址
     * @param Authorization
     * @param val
     * @param numbers
     * @param contract
     * @return
     */
    @POST("api/isTransferAccounts")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccountsByaddress(@Header("Authorization")String Authorization, @Field("val")double val, @Field("contract")String contract,@Field("keystorepsd")String keystorepsd,@Field("private_address")String private_address);



//    /**
//     * 判断keystore用户是否可以转账
//     * @param Authorization
//     * @param val
//     * @param numbers
//     * @param contract
//     * @return
//     */
//    @POST("api/isTransferAccounts")
//    @FormUrlEncoded
//    @Headers({"Accept:application/json"})
//    Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccountsByKeystore(@Header("Authorization")String Authorization, @Field("val")double val, @Field("numbers")long numbers, @Field("contract")String contract,@Field("keystorepsd")String keystorepsd);


    /**
     * 转账
     * @param Authorization
     * @param val
     * @param numbers
     * @param contract
     * @return
     */
    @POST("api/userTransferAccounts")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> userTransferAccounts(@Header("Authorization")String Authorization, @Field("val")double val, @Field("numbers")String numbers, @Field("contract")String contract,@Field("remarks")String remarks,@Field("keystorepsd")String keystorepsd);


    /**
     * 转账 地址
     * @param Authorization
     * @param val
     * @param numbers
     * @param contract
     * @return
     */
    @POST("api/userTransferAccounts")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> userTransferAccountsByaddress(@Header("Authorization")String Authorization, @Field("val")double val, @Field("contract")String contract,@Field("remarks")String remarks,@Field("keystorepsd")String keystorepsd,@Field("private_address")String private_address);


    /**
     * 获取个人资料
     * @param Authorization
     * @param numbers
     * @return
     */
    @POST("api/getUserInfo")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean<ToUser.DataBean>> getUserInfo(@Header("Authorization")String Authorization, @Field("numbers")String numbers);

    /**
     * 重置密码
     * @param mobile
     * @param password
     * @param password_confirmation
     * @param verification
     * @return
     */
    @POST("api/resetPassword")
    @Headers({"Accept:application/json"})
    @FormUrlEncoded
    Observable<RequestBean> resetPasswordbyMobile(@Field("mobile")String mobile, @Field("password")String password, @Field("password_confirmation")String password_confirmation, @Field("verification")String verification);
    /**
     * 重置密码
     * @param mobile
     * @param password
     * @param password_confirmation
     * @param verification
     * @return
     */
    @POST("api/resetPassword")
    @Headers({"Accept:application/json"})
    @FormUrlEncoded
    Observable<RequestBean> resetPasswordbyEmail(@Field("email")String email, @Field("password")String password, @Field("password_confirmation")String password_confirmation, @Field("verification")String verification);



    /**
     * 更新个人资料
     * @param Authorization
     * @param name
     * @param avatar
     * @param mobile
     * @return
     */
    @POST("api/updateUser")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> updateUser(@Header("Authorization")String Authorization,@Field("name")String name,@Field("avatar")String avatar,@Field("mobile")String mobile,@Field("verification")String verification);

    /**
     * 更新个人资料
     * @param Authorization
     * @param name
     * @param avatar
     * @param mobile
     * @return
     */
    @POST("api/updateUser")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> updateUsers(@Header("Authorization")String Authorization,@Field("mobile")String mobile,@Field("verification")String verification);



    /**
     * 更新个人资料
     * @param Authorization
     * @param name
     * @param avatar
     * @param mobile
     * @return
     */
    @POST("api/updateUser")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> updateUsersEmail(@Header("Authorization")String Authorization,@Field("email")String email,@Field("verification")String verification);

    @GET("api/versions/2")
    Observable<ApkBean> getVersion();

    @GET("api/userReferee")
    @Headers({"Accept:application/json"})
    Observable<RequestListBean<GroomBean.DataBean>> userReferee(@Header("Authorization")String Authorization);


    @GET("api/refreshWalletList")
    @Headers({"Accept:application/json"})
    Observable<RequestBean> refreshWalletList(@Header("Authorization")String Authorization);

    /*获取获取七牛Token*/
    @GET("qiniutoken")
    Observable<TokenBean> getQNToken();

    /**
     * 删除联系人
     * @param Authorization
     * @param numbers
     * @param name
     * @return
     */
    @POST("api/deltUserContact")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> deltUserContact(@Header("Authorization")String Authorization,@Field("numbers")String numbers,@Field("name")String name);



    @POST("api/updateKeystorePsd")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> updateKeystorePsd(@Header("Authorization")String Authorization,@Field("pwd")String pwd,@Field("new_pwd")String new_pwd);

    @POST("api/newSetKeystore")
    @FormUrlEncoded
    @Headers({"Accept:application/json"})
    Observable<RequestBean> newSetKeystore(@Header("Authorization")String Authorization,@Field("password")String password,@Field("password_confirmation")String password_confirmation);

    @POST("api/userInfo")
    @Headers({"Accept:application/json"})
    Observable<RequestBean<User.DataBean>> userInfo(@Header("Authorization")String Authorization);


    @POST("api/keysotreLogin")
    @Headers({"Accept:application/json"})
    @FormUrlEncoded
    Observable<RequestBean<User.DataBean>> keysotreLogin(@Field("keystore")String keystore,@Field("password")String password);


    @POST("api/isKeyStore")
    @Headers({"Accept:application/json"})
    @FormUrlEncoded
    Observable<RequestBean> isKeyStore(@Field("keystore")String keystore);

    @GET("api/changeLanguage")
    Observable<RequestBean> changeLanguage(@Query("type") String type);


    @POST("api/bindRegister")
    @Headers({"Accept:application/json"})
    @FormUrlEncoded
    Observable<RequestBean<User.DataBean>> bindRegister(@Field("name")String name, @Field("keystore")String keystore);


    /**
     * 单文件上传
     * @param description
     * @param file @Part MultipartBody.Part file 使用MultipartBody.Part类发送文件file到服务器
     * @return 状态信息String
     */
    @Multipart
    @POST("UploadServerAddr")
    Call<String> uploadFile(@Part("description") RequestBody description, @Part MultipartBody.Part file);

}
