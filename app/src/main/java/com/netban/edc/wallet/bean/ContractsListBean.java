package com.netban.edc.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Evan on 2018/8/16.
 */

public class ContractsListBean extends RequestListBean {


    public static class DataBean implements Parcelable{
        /**
         * id : 26
         * user_id : 1026
         * relation_id : 32
         * name :
         * numbers : 827995473
         * created_at : 2018-08-21 17:18:33
         * updated_at : 2018-08-21 17:18:33
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/1JTlmCgYPQjQjic65xQMMWDxXlpY8L4LDPTMnjiaPTWqApaoavXlGNyB0NkliaSgvB0BDx3kic8SRpBicagR3jdKic2w/132
         * username : 王莉
         */

        private int id;
        private int user_id;
        private int relation_id;
        private String name;
        private String numbers;
        private String created_at;
        private String updated_at;
        private String avatar;
        private String username;

        protected DataBean(Parcel in) {
            id = in.readInt();
            user_id = in.readInt();
            relation_id = in.readInt();
            name = in.readString();
            numbers = in.readString();
            created_at = in.readString();
            updated_at = in.readString();
            avatar = in.readString();
            username = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(user_id);
            dest.writeInt(relation_id);
            dest.writeString(name);
            dest.writeString(numbers);
            dest.writeString(created_at);
            dest.writeString(updated_at);
            dest.writeString(avatar);
            dest.writeString(username);
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getRelation_id() {
            return relation_id;
        }

        public void setRelation_id(int relation_id) {
            this.relation_id = relation_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumbers() {
            return numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
