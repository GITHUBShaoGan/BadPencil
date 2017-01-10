package com.slut.badpencil.database.dao.password;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class PassLabelBindDao {

    private static volatile PassLabelBindDao instances;
    private Dao<PassLabelBind, Integer> dao;

    public static PassLabelBindDao getInstances() {
        if (instances == null) {
            synchronized (PassLabelBindDao.class) {
                if (instances == null) {
                    instances = new PassLabelBindDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(PassLabelBind.class);
    }

    public void createSingle(PassLabelBind passLabelBind) throws SQLException {
        dao.create(passLabelBind);
    }

    public List<PassLabelBind> queryByPass(String uuid) throws SQLException {
        QueryBuilder<PassLabelBind, Integer> builder = dao.queryBuilder();
        builder.where().eq(PassLabelBind.Const.COLUMN_TITLE_PASS_UUID, uuid);
        return builder.query();
    }

    public void deleteByPasswordUUID(String uuid)throws SQLException{
        DeleteBuilder<PassLabelBind,Integer> builder = dao.deleteBuilder();
        builder.where().eq(PassLabelBind.Const.COLUMN_TITLE_PASS_UUID,uuid);
        builder.delete();
    }

}
