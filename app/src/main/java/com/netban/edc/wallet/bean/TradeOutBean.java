package com.netban.edc.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Evan on 2018/8/17.
 */

public class TradeOutBean extends RequestBean {


    public static class DataBean implements Parcelable {
        /**
         * name : 沈闯
         * avatar : https://resource.edc.org.cn/images/default_avatar.png
         * to_avatar : https://resource.edc.org.cn/images/default_avatar.png
         * to_name : 李小双
         * title : 黄渡
         */

        private String name;
        private String avatar;
        private String to_avatar;
        private String to_name;
        private String title;
        private String remark;
        private double num;
        private String number;
        private String private_address;
        private String contract_id;
        private String abbreviation;
        private String trade_pwd;


        protected DataBean(Parcel in) {
            name = in.readString();
            avatar = in.readString();
            to_avatar = in.readString();
            to_name = in.readString();
            title = in.readString();
            remark = in.readString();
            num = in.readDouble();
            number = in.readString();
            private_address = in.readString();
            contract_id = in.readString();
            abbreviation = in.readString();
            trade_pwd = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(avatar);
            dest.writeString(to_avatar);
            dest.writeString(to_name);
            dest.writeString(title);
            dest.writeString(remark);
            dest.writeDouble(num);
            dest.writeString(number);
            dest.writeString(private_address);
            dest.writeString(contract_id);
            dest.writeString(abbreviation);
            dest.writeString(trade_pwd);
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTo_avatar() {
            return to_avatar;
        }

        public void setTo_avatar(String to_avatar) {
            this.to_avatar = to_avatar;
        }

        public String getTo_name() {
            return to_name;
        }

        public void setTo_name(String to_name) {
            this.to_name = to_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrivate_address() {
            return private_address;
        }

        public void setPrivate_address(String private_address) {
            this.private_address = private_address;
        }

        public String getContract_id() {
            return contract_id;
        }

        public void setContract_id(String contract_id) {
            this.contract_id = contract_id;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getTrade_pwd() {
            return trade_pwd;
        }

        public void setTrade_pwd(String trade_pwd) {
            this.trade_pwd = trade_pwd;
        }
    }
}
