package com.netban.edc.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/2.
 */

public class KeystoreBean implements Serializable,Parcelable{

    /**
     * version : 3
     * id : 1708e71a-a2d2-4b83-8acc-9e434cedae86
     * address : 2c0e0b89350fdcbb633219d112bc964ed54a6ef2
     * Crypto : {"ciphertext":"97b6aba212afae80d42fe9e25bc45da04a0628a39dfc936c142fc68d20a2a711","cipherparams":{"iv":"5513770451db45401cce7a58b7d472e7"},"cipher":"aes-128-ctr","kdf":"scrypt","kdfparams":{"dklen":32,"salt":"441caa7fad4fd2b05459b3a8883b6966ed35a69e07742365bc6723bed81f2ce0","n":8192,"r":8,"p":1},"mac":"74e63b946adb2119be4496606a8e81e2f1fea9c7b79dc6e57cad4616500add6c"}
     */

    private int version;
    private String id;
    private String address;
    private CryptoBean Crypto;
    private CryptoBean crypto;
    protected KeystoreBean(Parcel in) {
        version = in.readInt();
        id = in.readString();
        address = in.readString();
        Crypto = in.readParcelable(CryptoBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(version);
        dest.writeString(id);
        dest.writeString(address);
        dest.writeParcelable(Crypto, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KeystoreBean> CREATOR = new Creator<KeystoreBean>() {
        @Override
        public KeystoreBean createFromParcel(Parcel in) {
            return new KeystoreBean(in);
        }

        @Override
        public KeystoreBean[] newArray(int size) {
            return new KeystoreBean[size];
        }
    };

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CryptoBean getCrypto() {
        return Crypto;
    }

    public void setCrypto(CryptoBean Crypto) {
        this.Crypto = Crypto;
    }

    public static class CryptoBean implements Serializable,Parcelable{
        /**
         * ciphertext : 97b6aba212afae80d42fe9e25bc45da04a0628a39dfc936c142fc68d20a2a711
         * cipherparams : {"iv":"5513770451db45401cce7a58b7d472e7"}
         * cipher : aes-128-ctr
         * kdf : scrypt
         * kdfparams : {"dklen":32,"salt":"441caa7fad4fd2b05459b3a8883b6966ed35a69e07742365bc6723bed81f2ce0","n":8192,"r":8,"p":1}
         * mac : 74e63b946adb2119be4496606a8e81e2f1fea9c7b79dc6e57cad4616500add6c
         */

        private String ciphertext;
        private CipherparamsBean cipherparams;
        private String cipher;
        private String kdf;
        private KdfparamsBean kdfparams;
        private String mac;


        protected CryptoBean(Parcel in) {
            ciphertext = in.readString();
            cipherparams = in.readParcelable(CipherparamsBean.class.getClassLoader());
            cipher = in.readString();
            kdf = in.readString();
            kdfparams = in.readParcelable(KdfparamsBean.class.getClassLoader());
            mac = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ciphertext);
            dest.writeParcelable(cipherparams, flags);
            dest.writeString(cipher);
            dest.writeString(kdf);
            dest.writeParcelable(kdfparams, flags);
            dest.writeString(mac);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CryptoBean> CREATOR = new Creator<CryptoBean>() {
            @Override
            public CryptoBean createFromParcel(Parcel in) {
                return new CryptoBean(in);
            }

            @Override
            public CryptoBean[] newArray(int size) {
                return new CryptoBean[size];
            }
        };

        public String getCiphertext() {
            return ciphertext;
        }

        public void setCiphertext(String ciphertext) {
            this.ciphertext = ciphertext;
        }

        public CipherparamsBean getCipherparams() {
            return cipherparams;
        }

        public void setCipherparams(CipherparamsBean cipherparams) {
            this.cipherparams = cipherparams;
        }

        public String getCipher() {
            return cipher;
        }

        public void setCipher(String cipher) {
            this.cipher = cipher;
        }

        public String getKdf() {
            return kdf;
        }

        public void setKdf(String kdf) {
            this.kdf = kdf;
        }

        public KdfparamsBean getKdfparams() {
            return kdfparams;
        }

        public void setKdfparams(KdfparamsBean kdfparams) {
            this.kdfparams = kdfparams;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public static class CipherparamsBean implements Serializable,Parcelable{
            /**
             * iv : 5513770451db45401cce7a58b7d472e7
             */

            private String iv;

            protected CipherparamsBean(Parcel in) {
                iv = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(iv);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<CipherparamsBean> CREATOR = new Creator<CipherparamsBean>() {
                @Override
                public CipherparamsBean createFromParcel(Parcel in) {
                    return new CipherparamsBean(in);
                }

                @Override
                public CipherparamsBean[] newArray(int size) {
                    return new CipherparamsBean[size];
                }
            };

            public String getIv() {
                return iv;
            }

            public void setIv(String iv) {
                this.iv = iv;
            }
        }

        public static class KdfparamsBean implements Serializable,Parcelable{
            /**
             * dklen : 32
             * salt : 441caa7fad4fd2b05459b3a8883b6966ed35a69e07742365bc6723bed81f2ce0
             * n : 8192
             * r : 8
             * p : 1
             */

            private int dklen;
            private String salt;
            private int n;
            private int r;
            private int p;

            protected KdfparamsBean(Parcel in) {
                dklen = in.readInt();
                salt = in.readString();
                n = in.readInt();
                r = in.readInt();
                p = in.readInt();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(dklen);
                dest.writeString(salt);
                dest.writeInt(n);
                dest.writeInt(r);
                dest.writeInt(p);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<KdfparamsBean> CREATOR = new Creator<KdfparamsBean>() {
                @Override
                public KdfparamsBean createFromParcel(Parcel in) {
                    return new KdfparamsBean(in);
                }

                @Override
                public KdfparamsBean[] newArray(int size) {
                    return new KdfparamsBean[size];
                }
            };

            public int getDklen() {
                return dklen;
            }

            public void setDklen(int dklen) {
                this.dklen = dklen;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public int getN() {
                return n;
            }

            public void setN(int n) {
                this.n = n;
            }

            public int getR() {
                return r;
            }

            public void setR(int r) {
                this.r = r;
            }

            public int getP() {
                return p;
            }

            public void setP(int p) {
                this.p = p;
            }
        }
    }
}
