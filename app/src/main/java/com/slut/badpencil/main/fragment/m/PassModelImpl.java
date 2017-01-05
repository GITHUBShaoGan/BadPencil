package com.slut.badpencil.main.fragment.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.config.LoadMoreType;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.utils.ResUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public class PassModelImpl implements PassModel {

    @Override
    public void loadMore(long pageNo, long pageSize, OnLoadMoreListener onLoadMoreListener) {
        if (pageNo < 1) {
            onLoadMoreListener.onLoadError(ResUtils.getString(R.string.error_invalid_pageno));
            return;
        }
        if (pageSize < 1) {
            onLoadMoreListener.onLoadError(ResUtils.getString(R.string.error_invalid_pagesize));
            return;
        }
        List<Password> passwordList = null;
        try {
            passwordList = PasswordDao.getInstances().queryByPage(pageNo, pageSize, false);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onLoadMoreListener.onLoadError(e.getLocalizedMessage());
            } else {
                onLoadMoreListener.onLoadError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
            return;
        }
        if (passwordList != null) {
            List<List<PassLabel>> passLabelLists = new ArrayList<>();
            try {
                for (Password password : passwordList) {
                    List<PassLabel> passLabels = new ArrayList<>();
                    List<PassLabelBind> passLabelBinds = PassLabelBindDao.getInstances().queryByPass(password.getUuid());
                    if (passLabelBinds != null) {
                        for (PassLabelBind passLabelBind : passLabelBinds) {
                            PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                            if (passLabel != null) {
                                passLabels.add(passLabel);
                            }
                        }
                    }
                    passLabelLists.add(passLabels);
                }
            } catch (SQLException e) {
                for (int i=0;i<passwordList.size();i++){
                    passLabelLists.add(new ArrayList<PassLabel>());
                }
            }
            if (passwordList.size() < pageSize) {
                onLoadMoreListener.onLoadSuccess(LoadMoreType.TYPE_END, passwordList, passLabelLists);
            } else {
                onLoadMoreListener.onLoadSuccess(LoadMoreType.TYPE_NOT_END, passwordList, passLabelLists);
            }
        } else {
            onLoadMoreListener.onLoadError(ResUtils.getString(R.string.error_query_failed));
        }
    }

}
