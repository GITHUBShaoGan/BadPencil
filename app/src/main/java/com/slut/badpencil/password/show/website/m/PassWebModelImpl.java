package com.slut.badpencil.password.show.website.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.database.dao.password.WebsitePassDao;
import com.slut.badpencil.utils.ResUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/10.
 */

public class PassWebModelImpl implements PassWebModel {

    @Override
    public void query(String uuid, OnQueryListener onQueryListener) {
        if (TextUtils.isEmpty(uuid)) {
            onQueryListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        Password password = null;
        WebsitePassword websitePassword = null;
        List<PassLabel> passLabelList = new ArrayList<>();
        try {
            password = PasswordDao.getInstances().querySingle(uuid);
            websitePassword = WebsitePassDao.getInstances().querySingle(uuid);
            List<PassLabelBind> passLabelBindList = PassLabelBindDao.getInstances().queryByPass(uuid);
            for (PassLabelBind passLabelBind : passLabelBindList) {
                PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                passLabelList.add(passLabel);
            }
            onQueryListener.onQuerySuccess(password, websitePassword, passLabelList);
        } catch (SQLException e) {
            onQueryListener.onQueryError(e.getLocalizedMessage());
        }
    }

}
