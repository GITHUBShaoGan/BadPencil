package com.slut.badpencil.database.bean.password;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */
@DatabaseTable
public class PassLabel {

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String name;
    @DatabaseField
    private String createStamp;

}
