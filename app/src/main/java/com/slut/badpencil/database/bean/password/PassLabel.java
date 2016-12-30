package com.slut.badpencil.database.bean.password;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */
@DatabaseTable
public class PassLabel implements Parcelable{

    protected PassLabel(Parcel in) {
        uuid = in.readString();
        name = in.readString();
        createStamp = in.readLong();
        updateStamp = in.readLong();
    }

    public static final Creator<PassLabel> CREATOR = new Creator<PassLabel>() {
        @Override
        public PassLabel createFromParcel(Parcel in) {
            return new PassLabel(in);
        }

        @Override
        public PassLabel[] newArray(int size) {
            return new PassLabel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(name);
        parcel.writeLong(createStamp);
        parcel.writeLong(updateStamp);
    }

    public class Const{
        public static final String COLUMN_TITLE_UUID = "uuid";
        public static final String COLUMN_TITLE_NAME = "name";
        public static final String COLUMN_TITLE_CREATESTAMP = "createStamp";
        public static final String COLUMN_TITLE_UPDATESTAMP = "updateStamp";
    }

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String name;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public PassLabel() {
    }

    public PassLabel(String uuid, String name, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.name = name;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "PassLabel{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", createStamp=" + createStamp +
                ", updateStamp=" + updateStamp +
                '}';
    }
}
