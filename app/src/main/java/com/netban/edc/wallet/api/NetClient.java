package com.netban.edc.wallet.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.netban.edc.wallet.base.Constant.Common.DEFAULT_TIMEOUT;
import static com.netban.edc.wallet.base.Constant.Uri.BASE_URL;

/**
 * Created by Evan on 2018/8/1.
 */

public class NetClient {
    private static NetClient instance;
    private NetApi netApi;

    public static NetClient getInstance(){
        if (instance==null){
            instance=new NetClient();
        }
        return instance;
    }

    private NetClient(){
        init();
    }

    private void init() {

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.client(configClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return super.responseBodyConverter(type, annotations, retrofit);
                    }

                    @Override
                    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
                        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
                    }

                    @Override
                    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return super.stringConverter(type, annotations, retrofit);
                    }
                })
                .build();
        netApi = retrofit.create(NetApi.class);
    }

    private OkHttpClient configClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addNetworkInterceptor(httpLoggingInterceptor);
        return okHttpClient.build();
    }

    public NetApi getNetApi(){
        return netApi;
    }

}
