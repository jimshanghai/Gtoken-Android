package com.netban.edc.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Evan on 2018/8/16.
 */

public class TradeListBean extends BaseBean implements Serializable {


    /**
     * code : 200
     * msg : ok
     * count : 2
     * data : [{"id":30,"txhash":"0xbf06bb3d5e07f3d1d3d64bac2214db93f95d2c474a87eb94514dd97f1ad34eb3","after":4,"remarks":"","type":1,"used_type":2,"used":3,"created_at":"2018-08-07 18:34:34","name":"小韩","avatar":null,"numbers":"8366867625"},{"id":4,"txhash":"0x90be384df0203348991c11090ee197f7a44e23a396373bbe09ba71322adfa752","after":1,"remarks":"","type":1,"used_type":2,"used":1,"created_at":"2018-08-07 15:55:09","name":"小韩","avatar":null,"numbers":"8366867625"}]
     * balance : 4
     * contracts : {"id":21,"college_id":14,"symbol":"yellow","name":"feldu","zh_name":"黄渡","filename":"HD","total":1000,"decimals":2,"callback_url":"192.168.0.51:82","treaty_address":"0x3432b60c5b448d9d6410c433cb49d02f6b483592","treaty_abi":"[{\"constant\":true,\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_from\",\"type\":\"address\"},{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"INITIAL_SUPPLY\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\"},{\"name\":\"_subtractedValue\",\"type\":\"uint256\"}],\"name\":\"decreaseApproval\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\"},{\"name\":\"_addedValue\",\"type\":\"uint256\"}],\"name\":\"increaseApproval\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\"},{\"name\":\"_spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"spender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"}]","icon":"https://resource.edc.org.cn/college/logo//1533643088453.jpeg?imageView2/1/w/200/h/200","brief":null,"state":1,"created_at":"2018-08-07 13:18:35","updated_at":"2018-08-08 10:07:06","token_paper":null,"remark":null,"urlname":"嗯嗯嗯嗯","token_url":"baidu.com"}
     * user_idO : 17
     */

    private int count;
    private double balance;
    private ContractsBean contracts;
    private int user_idO;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ContractsBean getContracts() {
        return contracts;
    }

    public void setContracts(ContractsBean contracts) {
        this.contracts = contracts;
    }

    public int getUser_idO() {
        return user_idO;
    }

    public void setUser_idO(int user_idO) {
        this.user_idO = user_idO;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ContractsBean implements Parcelable{
        /**
         * id : 21
         * college_id : 14
         * symbol : yellow
         * name : feldu
         * zh_name : 黄渡
         * filename : HD
         * total : 1000
         * decimals : 2
         * callback_url : 192.168.0.51:82
         * treaty_address : 0x3432b60c5b448d9d6410c433cb49d02f6b483592
         * treaty_abi : [{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint256"}],"name":"approve","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transferFrom","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"INITIAL_SUPPLY","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_subtractedValue","type":"uint256"}],"name":"decreaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"}],"name":"balanceOf","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transfer","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_addedValue","type":"uint256"}],"name":"increaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"},{"name":"_spender","type":"address"}],"name":"allowance","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"name":"owner","type":"address"},{"indexed":true,"name":"spender","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Transfer","type":"event"}]
         * icon : https://resource.edc.org.cn/college/logo//1533643088453.jpeg?imageView2/1/w/200/h/200
         * brief : null
         * state : 1
         * created_at : 2018-08-07 13:18:35
         * updated_at : 2018-08-08 10:07:06
         * token_paper : null
         * remark : null
         * urlname : 嗯嗯嗯嗯
         * token_url : baidu.com
         */

        private long id;
        private long college_id;
        private String symbol;
        private String name;
        private String zh_name;
        private String filename;
        private long total;
        private long decimals;
        private String callback_url;
        private String treaty_address;
        private String treaty_abi;
        private String icon;
        private String brief;
        private int state;
        private String created_at;
        private String updated_at;
        private String token_paper;
        private String remark;
        private String urlname;
        private String token_url;

        protected ContractsBean(Parcel in) {
            id = in.readLong();
            college_id = in.readLong();
            symbol = in.readString();
            name = in.readString();
            zh_name = in.readString();
            filename = in.readString();
            total = in.readLong();
            decimals = in.readLong();
            callback_url = in.readString();
            treaty_address = in.readString();
            treaty_abi = in.readString();
            icon = in.readString();
            brief = in.readString();
            state = in.readInt();
            created_at = in.readString();
            updated_at = in.readString();
            token_paper = in.readString();
            remark = in.readString();
            urlname = in.readString();
            token_url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(id);
            dest.writeLong(college_id);
            dest.writeString(symbol);
            dest.writeString(name);
            dest.writeString(zh_name);
            dest.writeString(filename);
            dest.writeLong(total);
            dest.writeLong(decimals);
            dest.writeString(callback_url);
            dest.writeString(treaty_address);
            dest.writeString(treaty_abi);
            dest.writeString(icon);
            dest.writeString(brief);
            dest.writeInt(state);
            dest.writeString(created_at);
            dest.writeString(updated_at);
            dest.writeString(token_paper);
            dest.writeString(remark);
            dest.writeString(urlname);
            dest.writeString(token_url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ContractsBean> CREATOR = new Creator<ContractsBean>() {
            @Override
            public ContractsBean createFromParcel(Parcel in) {
                return new ContractsBean(in);
            }

            @Override
            public ContractsBean[] newArray(int size) {
                return new ContractsBean[size];
            }
        };

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getCollege_id() {
            return college_id;
        }

        public void setCollege_id(long college_id) {
            this.college_id = college_id;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getZh_name() {
            return zh_name;
        }

        public void setZh_name(String zh_name) {
            this.zh_name = zh_name;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getDecimals() {
            return decimals;
        }

        public void setDecimals(long decimals) {
            this.decimals = decimals;
        }

        public String getCallback_url() {
            return callback_url;
        }

        public void setCallback_url(String callback_url) {
            this.callback_url = callback_url;
        }

        public String getTreaty_address() {
            return treaty_address;
        }

        public void setTreaty_address(String treaty_address) {
            this.treaty_address = treaty_address;
        }

        public String getTreaty_abi() {
            return treaty_abi;
        }

        public void setTreaty_abi(String treaty_abi) {
            this.treaty_abi = treaty_abi;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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

        public String getToken_paper() {
            return token_paper;
        }

        public void setToken_paper(String token_paper) {
            this.token_paper = token_paper;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUrlname() {
            return urlname;
        }

        public void setUrlname(String urlname) {
            this.urlname = urlname;
        }

        public String getToken_url() {
            return token_url;
        }

        public void setToken_url(String token_url) {
            this.token_url = token_url;
        }
    }

    public static class DataBean implements Parcelable{
        /**
         * id : 30
         * txhash : 0xbf06bb3d5e07f3d1d3d64bac2214db93f95d2c474a87eb94514dd97f1ad34eb3
         * after : 4
         * remarks :
         * type : 1
         * used_type : 2
         * used : 3
         * created_at : 2018-08-07 18:34:34
         * name : 小韩
         * avatar : null
         * numbers : 8366867625
         */

        private int id;
        private String txhash;
        private double after;
        private String remarks;
        private int type;
        private int used_type;
        private double used;
        private String created_at;
        private String name;
        private String avatar;
        private String numbers;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTxhash() {
            return txhash==null?"":txhash;
        }

        public void setTxhash(String txhash) {
            this.txhash = txhash;
        }

        public double getAfter() {
            return after;
        }

        public void setAfter(double after) {
            this.after = after;
        }

        public String getRemarks() {
            return remarks==null?"":remarks.trim();
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUsed_type() {
            return used_type;
        }

        public void setUsed_type(int used_type) {
            this.used_type = used_type;
        }

        public double getUsed() {
            return used;
        }

        public void setUsed(double used) {
            this.used = used;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
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

        public String getNumbers() {
            return numbers==null?"":numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        protected DataBean(Parcel in) {
            id = in.readInt();
            txhash = in.readString();
            after = in.readDouble();
            remarks = in.readString();
            type = in.readInt();
            used_type = in.readInt();
            used = in.readDouble();
            created_at = in.readString();
            name = in.readString();
            avatar = in.readString();
            numbers = in.readString();
            state = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(txhash);
            dest.writeDouble(after);
            dest.writeString(remarks);
            dest.writeInt(type);
            dest.writeInt(used_type);
            dest.writeDouble(used);
            dest.writeString(created_at);
            dest.writeString(name);
            dest.writeString(avatar);
            dest.writeString(numbers);
            dest.writeInt(state);
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
    }
}
