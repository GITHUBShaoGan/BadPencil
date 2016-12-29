package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */
@DatabaseTable
public class WifiPassword extends Password {

    @DatabaseField
    private String uuid;
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

    public WifiPassword(String uuid, String title, String account, String password, String remark, int type, long createStamp, long updateStamp, String routerIP, String routerPassword, String operatorAccount, String operatorPassword) {
        super(uuid, title, account, password, remark, type, createStamp, updateStamp);
        this.routerIP = routerIP;
        this.routerPassword = routerPassword;
        this.operatorAccount = operatorAccount;
        this.operatorPassword = operatorPassword;
        this.uuid = uuid;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
