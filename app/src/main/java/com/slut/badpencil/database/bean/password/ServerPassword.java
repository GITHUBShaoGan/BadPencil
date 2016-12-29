package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */
@DatabaseTable
public class ServerPassword extends Password{

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String address;
    @DatabaseField
    private int port;

    public ServerPassword() {
    }

    public ServerPassword(String uuid, String title, String account, String password, String remark, int type, long createStamp, long updateStamp, String address, int port) {
        super(uuid, title, account, password, remark, type, createStamp, updateStamp);
        this.address = address;
        this.port = port;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
