package com.netban.edc.wallet.bean;

/**
 * Created by Evan on 2018/9/7.
 */

public class BindKeyStoreBean extends RequestBean {

    public static class DataBean {
        /**
         * avatar : https://resource.edc.org.cn/images/default_avatar.png
         * numbers : 8204360508
         * name : netban123
         * sex : 0
         * mobile : null
         * email : null
         * type : 2
         * private_address : 0x7c02083b923096df36b0998c6452b6cb3a5c9d24
         * keystore : {
         "version": 3,
         "id": "4b0b0d41-e22c-4bdc-8805-3ea713a638a4",
         "address": "7c02083b923096df36b0998c6452b6cb3a5c9d24",
         "Crypto": {
         "ciphertext": "06e669768faf583077c757c69f2cbfaa8139c417dda5b31abdcab8148b77c917",
         "cipherparams": {
         "iv": "bcfc8883ac8ec6435df970989ae2ab9e"
         },
         "cipher": "aes-128-ctr",
         "kdf": "scrypt",
         "kdfparams": {
         "dklen": 32,
         "salt": "2255a6752ddb90c8065588b1e6a1c97de971a1465988a96c5ba5c78423125610",
         "n": 8192,
         "r": 8,
         "p": 1
         },
         "mac": "f52c8e70265731c101a3e611765599959b0734e22aab01b0a3f96f7d3915b267"
         }
         }
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk0OWQyZGQ1ZGMxNDg2MjYyMTFkOTI5YjcwOWNmOWY5NjUzYTkyZDE4ZDQ5YzBiMDEyZGNlZDhlYmE5MzJjYWVkMWRlOGYwZmEyMjlhNWZmIn0.eyJhdWQiOiI2IiwianRpIjoiOTQ5ZDJkZDVkYzE0ODYyNjIxMWQ5MjliNzA5Y2Y5Zjk2NTNhOTJkMThkNDljMGIwMTJkY2VkOGViYTkzMmNhZWQxZGU4ZjBmYTIyOWE1ZmYiLCJpYXQiOjE1MzYzMTQ0OTYsIm5iZiI6MTUzNjMxNDQ5NiwiZXhwIjoxNTY3ODUwNDk2LCJzdWIiOiIxMTY2NCIsInNjb3BlcyI6W119.nUTxsTQ-NqDBLbQmqeClmyKLrKdeunmXNSLF-CHQHy4i1D5rPHI1Ea_uYgBCd8bm593IJKMlxsGCbG8MiGXxYCbhS2Ll5Srw7i9cLGMA_RMPVxTdNaay6s3LNNeBKTNutdtFXXgAfQd_iBhdUYzs0-2KSUOvENl-IqXlL8g-8puAzw7xr-zSh0VPZit3PM_Le3G6O3HLc8PvPFedXTd14_bzQf_TfSpY38-hvffo3L7OKas1c1F63qQYdImUcP4sjbOE9O-_5Ks9A27yL7ln6TCwyFVYUtg76KYkF9ysgiZUI-ue5sDTQwqaQnDri_jI44UBC-ajGvsGuI-U0lGVFEja0RvodsUc6jG6-X-Fxh6CsULpEJbx6uVRwR-C1VMk2TlsFG02UYrfCn2zMZaUHl66QPvrPZTc1I4c_mIfYRDDgZB7jz6-XEhIXUJs_aEjhygZiQCp8j3qiUBqE6xWdp5s2MBF0-52v_iD-WtZUcCr8y-x9CsHbCDjuya6BQuFJUFe6ZTHpwth9M7fbMTOc-oEw9rQ8j9JNHwStmvrPoehXT60kvqKid_5E766M-E4wOPz2vPAogU3PYJG_D94dNye7Wqkp_zmbr3vjobadEW-Rh1XL1EiGB5LD4sijy5bwN65zmLdUmkYyCl7gAQl4VIq9_qmLqiEDqnobqF7okE
         */

        private String avatar;
        private String numbers;
        private String name;
        private int sex;
        private Object mobile;
        private Object email;
        private int type;
        private String private_address;
        private String keystore;
        private String token;

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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
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
    }
}
