package com.slut.badpencil.database.dao.password;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.UserConfig;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.dao.UserConfigDao;

import java.sql.SQLException;
import java.util.List;

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

    public void createSingle(Password password) throws SQLException {
        dao.create(password);
    }

    /**
     * 更新密码
     *
     * @param uuid
     * @param title
     * @param account
     * @param password
     * @param remark
     * @throws SQLException
     */
    public void updateSingle(String uuid, String title, String account, String password, String remark) throws SQLException {
        UpdateBuilder<Password, Integer> builder = dao.updateBuilder();
        builder.where().eq(Password.Const.COLUMN_UUID, uuid);
        builder.updateColumnValue(Password.Const.COLUMN_TITLE, title);
        builder.updateColumnValue(Password.Const.COLUMN_ACCOUNT, account);
        builder.updateColumnValue(Password.Const.COLUMN_PASSWORD, password);
        builder.updateColumnValue(Password.Const.COLUMN_REMARK, remark);
        builder.updateColumnValue(Password.Const.COLUMN_UPDATESTAMP, System.currentTimeMillis());
        builder.update();
    }

    /**
     * 根据页码和页面大小查询分页数据
     *
     * @param pageNo
     * @param pageSize
     * @param isDesc
     * @return
     * @throws SQLException
     */
    public List<Password> queryByPage(long pageNo, long pageSize, boolean isDesc) throws SQLException {
        QueryBuilder<Password, Integer> builder = dao.queryBuilder();
        builder.orderBy(Password.Const.COLUMN_CREATESTAMP, isDesc);
        long offSet = (pageNo - 1) * pageSize;
        builder.offset(offSet);
        builder.limit(pageSize);
        return builder.query();
    }
}
