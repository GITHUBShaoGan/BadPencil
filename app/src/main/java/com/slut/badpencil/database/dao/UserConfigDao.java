package com.slut.badpencil.database.dao;

import com.j256.ormlite.dao.Dao;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.UserConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */

public class UserConfigDao {

    private static volatile UserConfigDao instances;
    private Dao<UserConfig, Integer> dao;

    public static UserConfigDao getInstances() {
        if (instances == null) {
            synchronized (UserConfigDao.class) {
                if (instances == null) {
                    instances = new UserConfigDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(UserConfig.class);
    }

    public void createSingle(UserConfig userConfig) throws SQLException {
        dao.create(userConfig);
    }

    public boolean isConfigExists() throws SQLException {
        boolean flag = false;
        List<UserConfig> userConfigList = dao.queryForAll();
        if (userConfigList != null && !userConfigList.isEmpty()) {
            if (userConfigList.size() == 1) {
                flag = true;
            }
        }
        return flag;
    }
}
