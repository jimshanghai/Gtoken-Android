package com.netban.edc.wallet.bean;

/**
 * Created by Evan on 2018/9/18.
 */

public class GroomBean extends RequestBean {
    public static class DataBean {
        private String name;
        private String numbers;
        private String created_at;

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
    }
}
