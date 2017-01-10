package com.slut.badpencil.password.show.server.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.database.dao.password.ServerPasswordDao;
import com.slut.badpencil.utils.ResUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/6.
 */

public class PassServerModelImpl implements PassServerModel {

    @Override
    public void query(String uuid, OnQueryListener onQueryListener) {
        if (TextUtils.isEmpty(uuid)) {
            onQueryListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        Password password = null;
        try {
            password = PasswordDao.getInstances().querySingle(uuid);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onQueryListener.onQueryError(e.getLocalizedMessage());
            } else {
                onQueryListener.onQueryError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
            return;
        }
        if (password != null) {
            List<PassLabel> passLabelList = new ArrayList<>();
            try {
                List<PassLabelBind> passLabelBindList = PassLabelBindDao.getInstances().queryByPass(uuid);
                for (PassLabelBind passLabelBind : passLabelBindList) {
                    if (passLabelBind != null) {
                        PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                        if (passLabel != null) {
                            passLabelList.add(passLabel);
                        }
                    }
                }
            } catch (SQLException e) {

            }
            ServerPassword serverPassword = null;
            try {
                serverPassword = ServerPasswordDao.getInstances().querySingle(uuid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            onQueryListener.onQuerySuccess(password, serverPassword, passLabelList);
        } else {
            onQueryListener.onQueryError(ResUtils.getString(R.string.error_query_failed));
        }
    }

    @Override
    public void delete(String uuid, OnDeleteListener onDeleteListener) {
        if (TextUtils.isEmpty(uuid)) {
            onDeleteListener.onDeleteError(ResUtils.getString(R.string.error_cannot_delete_null));
            return;
        }
        try {
            PasswordDao.getInstances().deleteSingle(uuid);
            PassLabelBindDao.getInstances().deleteByPasswordUUID(uuid);
            ServerPasswordDao.getInstances().deleteSingle(uuid);
            onDeleteListener.onDeleteSuccess(uuid);
        } catch (SQLException e) {
            onDeleteListener.onDeleteError(e.getLocalizedMessage());
        }
    }

}
