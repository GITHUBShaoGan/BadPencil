package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */
@DatabaseTable
public class WebsitePassword extends Password {

    public class Const {
        public static final String COLUMN_PASS_UUID = "passUuid";
        public static final String COLUMN_URL = "url";
    }

    @DatabaseField
    private String passUuid;
    @DatabaseField
    private String url;

    public WebsitePassword() {

    }

    public WebsitePassword(String uuid, String title, String account, String password, String remark, int type, long createStamp, long updateStamp, String passUuid, String url) {
        super(uuid, title, account, password, remark, type, createStamp, updateStamp);
        this.passUuid = passUuid;
        this.url = url;
    }

    public String getPassUuid() {
        return passUuid;
    }

    public void setPassUuid(String passUuid) {
        this.passUuid = passUuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebsitePassword appendFullPass(Password password) {
        if (password != null) {
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
