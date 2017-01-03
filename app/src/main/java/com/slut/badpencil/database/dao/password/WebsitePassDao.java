package com.slut.badpencil.database.dao.password;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.WebsitePassword;

import java.sql.SQLException;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class WebsitePassDao {

    private static volatile WebsitePassDao instances;
    private Dao<WebsitePassword, Integer> dao;

    public static WebsitePassDao getInstances() {
        if (instances == null) {
            synchronized (WebsitePassDao.class) {
                if (instances == null) {
                    instances = new WebsitePassDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(WebsitePassword.class);
    }

    public void createSingle(WebsitePassword websitePassword) throws SQLException {
        dao.create(websitePassword);
    }

    public void updateSingle(String uuid,String url)throws SQLException{
        UpdateBuilder<WebsitePassword,Integer> builder = dao.updateBuilder();
        builder.where().eq(WebsitePassword.Const.COLUMN_PASS_UUID,uuid);
        builder.updateColumnValue(WebsitePassword.Const.COLUMN_URL,url);
        builder.update();
    }

}
