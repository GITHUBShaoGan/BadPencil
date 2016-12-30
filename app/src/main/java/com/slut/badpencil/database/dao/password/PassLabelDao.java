package com.slut.badpencil.database.dao.password;

import android.content.Intent;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class PassLabelDao {

    private static volatile PassLabelDao instances;
    private Dao<PassLabel, Integer> dao;

    public static PassLabelDao getInstances() {
        if (instances == null) {
            synchronized (PassLabelDao.class) {
                if (instances == null) {
                    instances = new PassLabelDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(PassLabel.class);
    }

    public void createSingle(PassLabel passLabel) throws SQLException {
        dao.create(passLabel);
    }

    public boolean queryByName(String name) throws SQLException {
        QueryBuilder<PassLabel, Integer> builder = dao.queryBuilder();
        builder.where().eq(PassLabel.Const.COLUMN_TITLE_NAME, name);
        PassLabel passLabel = builder.queryForFirst();
        if (passLabel == null) {
            return false;
        } else {
            return true;
        }
    }

    public PassLabel queryByUUID(String uuid) throws SQLException {
        QueryBuilder<PassLabel, Integer> builder = dao.queryBuilder();
        builder.where().eq(PassLabel.Const.COLUMN_TITLE_UUID, uuid);
        return builder.queryForFirst();
    }

    public List<PassLabel> queryByPage(long pageNo, long pageSize) throws SQLException {
        QueryBuilder<PassLabel, Integer> builder = dao.queryBuilder();
        long offSet = (pageNo - 1) * pageSize;
        builder.limit(pageSize);
        builder.offset(offSet);
        builder.orderBy(PassLabel.Const.COLUMN_TITLE_CREATESTAMP, false);
        return builder.query();
    }

}
