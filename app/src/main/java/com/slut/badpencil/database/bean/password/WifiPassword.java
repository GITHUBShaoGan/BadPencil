package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */
@DatabaseTable
public class WifiPassword extends Password {

    public class Const{
        public static final String COLUMN_PASS_UUID = "passUUID";
        public static final String COLUMN_ROUTER_IP = "routerIP";
        public static final String COLUMN_ROUTER_PASSWORD = "routerPassword";
        public static final String COLUMN_OPERATOR_ACCOUNT = "operatorAccount";
        public static final String COLUMN_OPERATOR_PASSWORD = "operatorPassword";
    }

    @DatabaseField
    private String passUUID;
    @DatabaseField
    private String routerIP;
    @DatabaseField
    private String routerPassword;
    @DatabaseField
    private String operatorAccount;
    @DatabaseField
    private String operatorPassword;

    public WifiPassword() {
    }

    public WifiPassword(String passUUID, String routerIP, String routerPassword, String operatorAccount, String operatorPassword) {
        this.passUUID = passUUID;
        this.routerIP = routerIP;
        this.routerPassword = routerPassword;
        this.operatorAccount = operatorAccount;
        this.operatorPassword = operatorPassword;
    }

    public String getPassUUID() {
        return passUUID;
    }

    public void setPassUUID(String passUUID) {
        this.passUUID = passUUID;
    }

    public String getRouterIP() {
        return routerIP;
    }

    public void setRouterIP(String routerIP) {
        this.routerIP = routerIP;
    }

    public String getRouterPassword() {
        return routerPassword;
    }

    public void setRouterPassword(String routerPassword) {
        this.routerPassword = routerPassword;
    }

    public String getOperatorAccount() {
        return operatorAccount;
    }

    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount;
    }

    public String getOperatorPassword() {
        return operatorPassword;
    }

    public void setOperatorPassword(String operatorPassword) {
        this.operatorPassword = operatorPassword;
    }

    public WifiPassword appendFullPass(Password password){
        if(password!=null) {
            this.setUuid(password.getUuid());
            this.setUpdateStamp(password.getUpdateStamp());
            this.setCreateStamp(password.getCreateStamp());
            this.setRemark(password.getRemark());
            this.setAccount(password.getAccount());
            this.setPassword(password.getPassword());
            this.setTitle(password.getTitle());
            this.setType(Type.SERVER);
        }
        return this;
    }
}
