package com.slut.badpencil.database.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */
@DatabaseTable
public class UserConfig {

    public class UnlockType{
        public static final int TEXT = 1;
        public static final int PATTERN = 2;
        public static final int FINGERPRINT = 3;
    }

    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private boolean isPatternEnable;
    @DatabaseField
    private String patternPassword;
    @DatabaseField
    private boolean isTextEnable;
    @DatabaseField
    private String textPassword;
    @DatabaseField
    private int preferUnlockType;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public UserConfig() {
    }

    public UserConfig(String uuid, boolean isPatternEnable, String patternPassword, boolean isTextEnable, String textPassword, int preferUnlockType, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.isPatternEnable = isPatternEnable;
        this.patternPassword = patternPassword;
        this.isTextEnable = isTextEnable;
        this.textPassword = textPassword;
        this.preferUnlockType = preferUnlockType;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isPatternEnable() {
        return isPatternEnable;
    }

    public void setPatternEnable(boolean patternEnable) {
        isPatternEnable = patternEnable;
    }

    public String getPatternPassword() {
        return patternPassword;
    }

    public void setPatternPassword(String patternPassword) {
        this.patternPassword = patternPassword;
    }

    public boolean isTextEnable() {
        return isTextEnable;
    }

    public void setTextEnable(boolean textEnable) {
        isTextEnable = textEnable;
    }

    public String getTextPassword() {
        return textPassword;
    }

    public void setTextPassword(String textPassword) {
        this.textPassword = textPassword;
    }

    public int getPreferUnlockType() {
        return preferUnlockType;
    }

    public void setPreferUnlockType(int preferUnlockType) {
        this.preferUnlockType = preferUnlockType;
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
