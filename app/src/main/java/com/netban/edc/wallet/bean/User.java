package com.netban.edc.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Evan on 2018/8/16.
 */

public class User extends RequestBean {


    public static class DataBean implements Parcelable,Serializable{
        /**
         * avatar : https://resource.edc.org.cn/images/default_avatar.png
         * numbers : 8574645850
         * name : yyuu
         * sex : 1
         * mobile : null
         * email : 1308670469@qq.com
         * type : 1
         * private_address : 0x7CA1Ae8548A1ea89965518Ad87dfB315A871E27b
         * keystore : {"version":3,"id":"96f335ed-3bf8-4e0e-b542-837ac1c44334","address":"7ca1ae8548a1ea89965518ad87dfb315a871e27b","crypto":{"ciphertext":"9dbd754961d6f8c09f20e5fbccaeaf7f686f21a0341b30b2e36bc1910404c1c6","cipherparams":{"iv":"dc27c9c7621e9c932b2493b942805a56"},"cipher":"aes-128-ctr","kdf":"scrypt","kdfparams":{"dklen":32,"salt":"35184a6b2ba63687dfd2f23ca63903f913d68cd03308276f0b2e4b5c61a00caa","n":8192,"r":8,"p":1},"mac":"605bc177c36961aa0dccec0f8e0d0a24fb72c63b14d5d41ae4c42fc292dcbb88"}}
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImMyYzcxOTI0Y2ZlY2Q1NmZlNjQ5NWRjM2IzNjg1NDIwOGIxODVmNDhiOWJmYzI3Yjg4ZDM1NTQwMjJjMmViMmE0YzI3NGE3ZTliOTAyYjkxIn0.eyJhdWQiOiI2IiwianRpIjoiYzJjNzE5MjRjZmVjZDU2ZmU2NDk1ZGMzYjM2ODU0MjA4YjE4NWY0OGI5YmZjMjdiODhkMzU1NDAyMmMyZWIyYTRjMjc0YTdlOWI5MDJiOTEiLCJpYXQiOjE1MzU5NDQzOTQsIm5iZiI6MTUzNTk0NDM5NCwiZXhwIjoxNTY3NDgwMzk0LCJzdWIiOiIxMTUwMCIsInNjb3BlcyI6W119.YmxVB-1fhA715ZNxgJ6DYq_MrFhYuRznTm0S849v31Y4JHkUu01dzJMdWNvkQtcFh4i52nTCiIECgmUkh09Ap5SYsAoggjBJ-jp-iKsXDvOiRgWpo-UijURKiksClwUpKJlVO1tIQStOIgdDkjAdajMVWtBsnVglHxyr__5lOX0qy2vmEl5zI79pIkm2yKZvha8obyq6nS5td7PvJxmGrcT5CYn0eraIAKLLRw4rf-ioYrcOuKPVBsEdcnsOlmnvoqA-vYvls8Pleoe6lxwRD0YhoZrFjCOzXVJdiQmSTE3-QRSNk-JXfCa3uUk2eIsMd-93bwW5JGZ92Ij1CqtP_3WAGQ1fQHL1tEJ68hBCQ2qxhOCSLUEM5fQDka4AbqFx3KXPjrkgdNvD86lj__3iIc-HetjQ-M011CQC5nQ9NKzuSxtK6FqmmZSjcfqQcjkAOa2xnubufbwqDHKmadFjBNMHmYkqTJqZVeDpCpUM3WSWPW1bzZO5oYzASl01-RI0oD-e742jfHYBTt3x3YLX0Ij4T9SM9c5UtrQ6hWDr0-Uw52HlHvDekDgiqsaiCzyy2TxXzfP6jb_Ok5kE3SBASAauUay-BrOM1xpR0rzH7Uf9sinJFK_nFAMY0_YuDCqz6LzNxblNZmGFlBMdGsHLXpYSQ1Ah2r-jAH-_bzXcJ9U
         */

        private String avatar;
        private String numbers;
        private String name;
        private String sex;
        private String mobile;
        private String email;
        private String type;
        private String private_address;
        private String keystore;
        private String token;
        private boolean isdown;

        protected DataBean(Parcel in) {
            avatar = in.readString();
            numbers = in.readString();
            name = in.readString();
            sex = in.readString();
            mobile = in.readString();
            email = in.readString();
            type = in.readString();
            private_address = in.readString();
            keystore = in.readString();
            token = in.readString();
            isdown = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(avatar);
            dest.writeString(numbers);
            dest.writeString(name);
            dest.writeString(sex);
            dest.writeString(mobile);
            dest.writeString(email);
            dest.writeString(type);
            dest.writeString(private_address);
            dest.writeString(keystore);
            dest.writeString(token);
            dest.writeByte((byte) (isdown ? 1 : 0));
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNumbers() {
            return numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrivate_address() {
            return private_address;
        }

        public void setPrivate_address(String private_address) {
            this.private_address = private_address;
        }

        public String getKeystore() {
            return keystore;
        }

        public void setKeystore(String keystore) {
            this.keystore = keystore;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isIsdown() {
            return isdown;
        }

        public void setIsdown(boolean isdown) {
            this.isdown = isdown;
        }
    }
}
