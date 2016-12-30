package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */
@DatabaseTable
public class WebsitePassword extends Password{

    @DatabaseField(id = true)
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
}
