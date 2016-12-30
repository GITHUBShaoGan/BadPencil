package com.slut.badpencil.database.dao.password;

import com.j256.ormlite.dao.Dao;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.UserConfig;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.dao.UserConfigDao;

import java.sql.SQLException;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class PasswordDao {

    private static volatile PasswordDao instances;
    private Dao<Password, Integer> dao;

    public static PasswordDao getInstances() {
        if (instances == null) {
            synchronized (PasswordDao.class) {
                if (instances == null) {
                    instances = new PasswordDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(Password.class);
    }

    public void createSingle(Password password)throws SQLException{
        dao.create(password);
    }
}
