package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */
@DatabaseTable
public class ServerPassword extends Password{

    public class Const{
        public static final String COLUMN_PASS_UUID ="passUUID";
        public static final String COLUMN_ADDRESS ="address";
        public static final String COLUMN_PORT ="port";
    }

    @DatabaseField
    private String passUUID;
    @DatabaseField
    private String address;
    @DatabaseField
    private int port;

    public ServerPassword() {
    }

    public ServerPassword(String uuid, String title, String account, String password, String remark, int type, long createStamp, long updateStamp, String passUUID, String address, int port) {
        super(uuid, title, account, password, remark, type, createStamp, updateStamp);
        this.passUUID = passUUID;
        this.address = address;
        this.port = port;
    }

    public String getPassUUID() {
        return passUUID;
    }

    public void setPassUUID(String passUUID) {
        this.passUUID = passUUID;
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

    public ServerPassword appendFullPass(Password password){
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
