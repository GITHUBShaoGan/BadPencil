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
                for (int i = 0; i < passwordList.size(); i++) {
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

    @Override
    public void notifyItemChanged(String uuid, List<Password> passwordList, OnNotifyItemChangeListener onNotifyItemChangeListener) {
        if (TextUtils.isEmpty(uuid)) {
            onNotifyItemChangeListener.notifyItemChangeError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        if (passwordList == null || passwordList.isEmpty()) {
            onNotifyItemChangeListener.notifyItemChangeError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        int position = -1;
        for (int i = 0; i < passwordList.size(); i++) {
            if (passwordList.get(i).getUuid().equals(uuid)) {
                position = i;
                break;
            }
        }
        if (position == -1) {
            onNotifyItemChangeListener.notifyItemChangeError(ResUtils.getString(R.string.error_query_failed));
        } else {
            Password password = null;
            List<PassLabel> passLabelList = new ArrayList<>();
            try {
                password = PasswordDao.getInstances().querySingle(uuid);
                List<PassLabelBind> passLabelBindList = null;
                passLabelBindList = PassLabelBindDao.getInstances().queryByPass(uuid);
                for (PassLabelBind passLabelBind : passLabelBindList) {
                    PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                    passLabelList.add(passLabel);
                }
            } catch (SQLException e) {
                onNotifyItemChangeListener.notifyItemChangeError(e.getLocalizedMessage());
                return;
            }
            onNotifyItemChangeListener.notifyItemChangeSuccess(position, password, passLabelList);
        }
    }

    @Override
    public void notifyItemInsert(String uuid, OnNotifyItemInsertListener onNotifyItemInsertListener) {
        if (TextUtils.isEmpty(uuid)) {
            onNotifyItemInsertListener.notifyItemInsertError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        try {
            Password password = PasswordDao.getInstances().querySingle(uuid);
            List<PassLabel> passLabelList = new ArrayList<>();
            List<PassLabelBind> passLabelBindList = PassLabelBindDao.getInstances().queryByPass(uuid);
            for (PassLabelBind passLabelBind : passLabelBindList) {
                PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                passLabelList.add(passLabel);
            }
            onNotifyItemInsertListener.notifyItemInsertSuccess(password, passLabelList);
        } catch (SQLException e) {
            onNotifyItemInsertListener.notifyItemInsertError(e.getLocalizedMessage());
        }
    }

    @Override
    public void notifyItemRemove(String uuid, List<Password> passwordList, OnNotifyItemRemoveListener onNotifyItemRemoveListener) {
        if (TextUtils.isEmpty(uuid)) {
            onNotifyItemRemoveListener.notifyItemRemoveError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        if (passwordList == null || passwordList.isEmpty()) {
            onNotifyItemRemoveListener.notifyItemRemoveError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        int position = -1;
        for (int i = 0; i < passwordList.size(); i++) {
            if (passwordList.get(i).getUuid().equals(uuid)) {
                position = i;
                break;
            }
        }
        if (position == -1) {
            onNotifyItemRemoveListener.notifyItemRemoveError(ResUtils.getString(R.string.error_query_failed));
        } else {
            onNotifyItemRemoveListener.notifyItemRemoveSuccess(position);
        }
    }

}
