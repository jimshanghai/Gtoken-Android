package com.netban.edc.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Evan on 2018/8/17.
 */

public class QrcodeBean implements Parcelable{
    private String number;
    private String avater;
    private String name;
    protected QrcodeBean(Parcel in) {
        number = in.readString();
        avater = in.readString();
        name=in.readString();
    }
    public QrcodeBean(){

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(avater);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QrcodeBean> CREATOR = new Creator<QrcodeBean>() {
        @Override
        public QrcodeBean createFromParcel(Parcel in) {
            return new QrcodeBean(in);
        }

        @Override
        public QrcodeBean[] newArray(int size) {
            return new QrcodeBean[size];
        }
    };

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
