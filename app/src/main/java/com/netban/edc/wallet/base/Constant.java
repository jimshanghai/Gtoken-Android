package com.netban.edc.wallet.base;

/**
 * Created by Evan on 2018/7/31.
 */

public final class Constant {
    public interface Code {
        int CODE_REQUEST_PERMISSION = 101;
    }

    public interface Common {
        int DEFAULT_TIMEOUT = 5;
    }

    public interface Uri {
         String BASE_UrL_TEST=" http://192.168.0.51:81/";//内网
         String BASE_UrL_SERVIER="";//服务器
         String BASE_URL_OTEST="https://wallet.edc.org.cn/";//外网
         String BASE_URL = BASE_URL_OTEST;
         String URL_PACT = "https://www.gonggawallet.com/#/Terms";
         String PATH_QINIU="https://resource.edc.org.cn/";//七牛
         String PATH_EDC="https://edc.org.cn";
         String URL_APK="https://gongga.org/down/Gtoken.apk";
//         String URL_APK="https://edc.org.cn/EDCApp.apk";
    }

    public interface Type {
       int TYPE_PACT = 1;
    }


}
