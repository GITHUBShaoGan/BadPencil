package com.slut.badpencil.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.slut.badpencil.App;
import com.slut.badpencil.database.bean.UserConfig;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.database.bean.password.WebsitePassword;
import com.slut.badpencil.database.bean.password.WifiPassword;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static volatile DBHelper instances;
    private Map<String, Dao> daoMap = new HashMap<>();

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    public static synchronized DBHelper getHelper() {
        if (instances == null) {
            synchronized (DBHelper.class) {
                if (instances == null) {
                    instances = new DBHelper(App.getContext(), DBConfig.DB_NAME, null, DBConfig.DB_VERSION);
                }
            }
        }
        return instances;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, UserConfig.class);
            TableUtils.createTableIfNotExists(connectionSource, Password.class);
            TableUtils.createTableIfNotExists(connectionSource, PassLabel.class);
            TableUtils.createTableIfNotExists(connectionSource, PassLabelBind.class);
            TableUtils.createTableIfNotExists(connectionSource, WebsitePassword.class);
            TableUtils.createTableIfNotExists(connectionSource, ServerPassword.class);
            TableUtils.createTableIfNotExists(connectionSource, WifiPassword.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public synchronized Dao getDao(Class cls) {
        Dao dao = null;
        String className = cls.getSimpleName();
        if (daoMap.containsKey(className)) {
            dao = daoMap.get(className);
        }
        if (dao == null) {
            try {
                dao = super.getDao(cls);
                daoMap.put(className, dao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daoMap.keySet()) {
            Dao dao = daoMap.get(key);
            dao = null;
        }
    }

}
