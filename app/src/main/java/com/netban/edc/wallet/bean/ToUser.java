package com.netban.edc.wallet.bean;

/**
 * Created by Evan on 2018/8/20.
 */

public class ToUser extends RequestBean {

    public static class DataBean {
        /**
         * numbers : 788867912
         * name : 韩
         * avatar : https://resource.edc.org.cn/images/default_avatar.png
         */

        private String numbers;
        private String name;
        private String avatar;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}

