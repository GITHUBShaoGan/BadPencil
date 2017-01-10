package com.slut.badpencil.database.dao.password;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.password.ServerPassword;

import java.sql.SQLException;

/**
 * Created by shijianan on 2017/1/4.
 */

public class ServerPasswordDao {

    private static volatile ServerPasswordDao instances;
    private Dao<ServerPassword, Integer> dao;

    public static ServerPasswordDao getInstances() {
        if (instances == null) {
            synchronized (ServerPasswordDao.class) {
                if (instances == null) {
                    instances = new ServerPasswordDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(ServerPassword.class);
    }

    public void createSingle(ServerPassword serverPassword)throws SQLException{
        dao.create(serverPassword);
    }

    public void updateSingle(String uuid,String ip,String port)throws SQLException{
        UpdateBuilder<ServerPassword,Integer> builder = dao.updateBuilder();
        builder.where().eq(ServerPassword.Const.COLUMN_PASS_UUID,uuid);
        builder.updateColumnValue(ServerPassword.Const.COLUMN_ADDRESS,ip);
        builder.updateColumnValue(ServerPassword.Const.COLUMN_PORT,port);
        builder.update();
    }

    public ServerPassword querySingle(String uuid)throws SQLException{
        QueryBuilder<ServerPassword,Integer> builder = dao.queryBuilder();
        builder.where().eq(ServerPassword.Const.COLUMN_PASS_UUID,uuid);
        return builder.queryForFirst();
    }

    public void deleteSingle(String uuid)throws SQLException{
        DeleteBuilder<ServerPassword,Integer> builder = dao.deleteBuilder();
        builder.where().eq(ServerPassword.Const.COLUMN_PASS_UUID,uuid);
        builder.delete();
    }
}
