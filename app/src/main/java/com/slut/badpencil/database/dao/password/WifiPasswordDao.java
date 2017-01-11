package com.slut.badpencil.database.dao.password;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;

import java.sql.SQLException;

/**
 * Created by shijianan on 2017/1/11.
 */

public class WifiPasswordDao {

    private static volatile WifiPasswordDao instances;
    private Dao<WifiPassword, Integer> dao;

    public static WifiPasswordDao getInstances() {
        if (instances == null) {
            synchronized (WifiPasswordDao.class) {
                if (instances == null) {
                    instances = new WifiPasswordDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(WifiPassword.class);
    }

    public void createSingle(WifiPassword wifiPassword) throws SQLException {
        dao.create(wifiPassword);
    }

    public WifiPassword querySingle(String uuid) throws SQLException {
        QueryBuilder<WifiPassword, Integer> builder = dao.queryBuilder();
        builder.where().eq(WifiPassword.Const.COLUMN_PASS_UUID, uuid);
        return builder.queryForFirst();
    }

    public void deleteByPasswordUUID(String uuid) throws SQLException {
        DeleteBuilder<WifiPassword, Integer> builder = dao.deleteBuilder();
        builder.where().eq(WifiPassword.Const.COLUMN_PASS_UUID, uuid);
        builder.delete();
    }

    public void updateSingle(String uuid, String routerIP, String routerPswd, String operatorAccount, String operatorPswd) throws SQLException {
        UpdateBuilder<WifiPassword, Integer> builder = dao.updateBuilder();
        builder.where().eq(WifiPassword.Const.COLUMN_PASS_UUID, uuid);
        builder.updateColumnValue(WifiPassword.Const.COLUMN_ROUTER_IP, routerIP);
        builder.updateColumnValue(WifiPassword.Const.COLUMN_ROUTER_PASSWORD, routerPswd);
        builder.updateColumnValue(WifiPassword.Const.COLUMN_OPERATOR_ACCOUNT, operatorAccount);
        builder.updateColumnValue(WifiPassword.Const.COLUMN_OPERATOR_PASSWORD, operatorPswd);
        builder.update();
    }
}
