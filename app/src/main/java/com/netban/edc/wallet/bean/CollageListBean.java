package com.netban.edc.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Evan on 2018/8/16.
 */

public class CollageListBean extends RequestListBean {


    public static class DataBean implements Parcelable{
        /**
         * balance : 0.09
         * icon : https://resource.edc.org.cn/college/logo/2/1533474498835.jpg?imageView2/1/w/200/h/200
         * zh_name : 网班通证
         * symbol : nn
         * contract_id : eyJpdiI6InBkRmFPZjd4N2d4d1BveHdRTTNBNmc9PSIsInZhbHVlIjoiU0xMTnRMSEpXXC93ZTBITU9NQ09VbGc9PSIsIm1hYyI6Ijc1MTE0NjkzY2ZkZGRmNjU4Y2I0ZTYxODc2NWQ4OThiNTcxNjEzN2ZlNDI4Y2Q5N2Q5YjlhZjhhY2E0YjcxOTUifQ==
         */

        private double balance;
        private String icon;
        private String zh_name;
        private String symbol;
        private String contract_id;

        protected DataBean(Parcel in) {
            balance = in.readDouble();
            icon = in.readString();
            zh_name = in.readString();
            symbol = in.readString();
            contract_id = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(balance);
            dest.writeString(icon);
            dest.writeString(zh_name);
            dest.writeString(symbol);
            dest.writeString(contract_id);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getZh_name() {
            return zh_name;
        }

        public void setZh_name(String zh_name) {
            this.zh_name = zh_name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getContract_id() {
            return contract_id;
        }

        public void setContract_id(String contract_id) {
            this.contract_id = contract_id;
        }
    }
}
