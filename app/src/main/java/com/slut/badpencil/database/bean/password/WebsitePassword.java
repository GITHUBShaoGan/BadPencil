package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */
@DatabaseTable
public class WebsitePassword extends Password {

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String url;

    public WebsitePassword() {

    }

    public WebsitePassword(String uuid, String title, String account, String password, String remark, int type, long createStamp, long updateStamp, String url) {
        super(uuid, title, account, password, remark, type, createStamp, updateStamp);
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
