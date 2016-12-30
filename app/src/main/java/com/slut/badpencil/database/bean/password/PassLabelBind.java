package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */
@DatabaseTable
public class PassLabelBind {

    public class Const{
        public static final String COLUMN_TITLE_UUID = "uuid";
        public static final String COLUMN_TITLE_PASS_UUID = "passUuid";
        public static final String COLUMN_TITLE_LABEL_UUID = "labelUuid";
        public static final String COLUMN_TITLE_CREATESTAMP_UUID = "createStamp";
    }

    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private String passUuid;
    @DatabaseField
    private String labelUuid;
    @DatabaseField
    private long createStamp;

    public PassLabelBind() {
    }

    public PassLabelBind(String uuid, String passUuid, String labelUuid, long createStamp) {
        this.uuid = uuid;
        this.passUuid = passUuid;
        this.labelUuid = labelUuid;
        this.createStamp = createStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassUuid() {
        return passUuid;
    }

    public void setPassUuid(String passUuid) {
        this.passUuid = passUuid;
    }

    public String getLabelUuid() {
        return labelUuid;
    }

    public void setLabelUuid(String labelUuid) {
        this.labelUuid = labelUuid;
    }

    public long getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(long createStamp) {
        this.createStamp = createStamp;
    }

    @Override
    public String toString() {
        return "PassLabelBind{" +
                "uuid='" + uuid + '\'' +
                ", passUuid='" + passUuid + '\'' +
                ", labelUuid='" + labelUuid + '\'' +
                ", createStamp=" + createStamp +
                '}';
    }
}
