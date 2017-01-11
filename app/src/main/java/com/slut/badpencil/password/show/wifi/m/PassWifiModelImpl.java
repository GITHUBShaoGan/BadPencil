package com.slut.badpencil.password.show.wifi.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.database.dao.password.WifiPasswordDao;
import com.slut.badpencil.utils.ResUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/11.
 */

public class PassWifiModelImpl implements PassWifiModel {

    @Override
    public void query(String uuid, OnQueryListener onQueryListener) {
        if (TextUtils.isEmpty(uuid)) {
            onQueryListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        try {
            Password password = PasswordDao.getInstances().querySingle(uuid);
            WifiPassword wifiPassword = WifiPasswordDao.getInstances().querySingle(uuid);
            List<PassLabel> passLabelList = new ArrayList<>();
            List<PassLabelBind> passLabelBindList = PassLabelBindDao.getInstances().queryByPass(uuid);
            for (PassLabelBind passLabelBind : passLabelBindList) {
                if (passLabelBind != null) {
                    PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                    if (passLabel != null) {
                        passLabelList.add(passLabel);
                    }
                }
            }
            onQueryListener.onQuerySuccess(password, wifiPassword, passLabelList);
        } catch (SQLException e) {
            onQueryListener.onQueryError(e.getLocalizedMessage());
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
            WifiPasswordDao.getInstances().deleteByPasswordUUID(uuid);
            onDeleteListener.onDeleteSuccess(uuid);
        } catch (SQLException e) {
            onDeleteListener.onDeleteError(e.getLocalizedMessage());
        }
    }

}
