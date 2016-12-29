package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */
@DatabaseTable
public class Password {

    /**
     * 密码的类型
     */
    public class Type {
        public static final int DEFAULT = 0;
        public static final int WEBSITE = 1;
        public static final int BANK = 2;
        public static final int SEVER = 3;
        public static final int WIFI = 4;
    }

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String title;
    @DatabaseField
    private String account;
    @DatabaseField
    private String password;
    @DatabaseField
    private String remark;
    @DatabaseField
    private int type;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public Password() {
    }

    public Password(String uuid, String title, String account, String password, String remark, int type, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.title = title;
        this.account = account;
        this.password = password;
        this.remark = remark;
        this.type = type;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(long createStamp) {
        this.createStamp = createStamp;
    }

    public long getUpdateStamp() {
        return updateStamp;
    }

    public void setUpdateStamp(long updateStamp) {
        this.updateStamp = updateStamp;
    }
}
