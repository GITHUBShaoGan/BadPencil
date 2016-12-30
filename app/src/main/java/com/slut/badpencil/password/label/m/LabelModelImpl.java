package com.slut.badpencil.password.label.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.utils.ResUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class LabelModelImpl implements LabelModel {

    @Override
    public void create(String name, OnCreateListener onCreateListener) {
        if (TextUtils.isEmpty(name)) {
            onCreateListener.onCreateError(ResUtils.getString(R.string.error_label_name_cannot_empty));
            return;
        }
        boolean isExist = false;
        try {
            isExist = PassLabelDao.getInstances().queryByName(name);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onCreateListener.onCreateError(e.getLocalizedMessage());
            } else {
                onCreateListener.onCreateError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
            return;
        }
        if (isExist) {
            onCreateListener.onCreateExists();
            return;
        } else {
            String uuid = UUID.randomUUID().toString();
            long stamp = System.currentTimeMillis();
            PassLabel passLabel = new PassLabel(uuid, name, stamp, stamp);
            try {
                PassLabelDao.getInstances().createSingle(passLabel);
                onCreateListener.onCreateSuccess(passLabel);
            } catch (SQLException e) {
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onCreateListener.onCreateError(e.getLocalizedMessage());
                } else {
                    onCreateListener.onCreateError(ResUtils.getString(R.string.error_unknown_exception_happened));
                }
            }
        }
    }

    @Override
    public void load(long pageNO, long pageSize, ArrayList<PassLabel> passLabelArrayList, OnLoadListener onLoadListener) {
        if (pageNO < 1) {
            onLoadListener.onLoadError(ResUtils.getString(R.string.error_invalid_pageno));
            return;
        }
        if (pageSize < 1) {
            onLoadListener.onLoadError(ResUtils.getString(R.string.error_invalid_pagesize));
            return;
        }
        List<PassLabel> passLabels = null;
        try {
            passLabels = PassLabelDao.getInstances().queryByPage(pageNO, pageSize);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onLoadListener.onLoadError(e.getLocalizedMessage());
            } else {
                onLoadListener.onLoadError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
            return;
        }
        if (passLabels != null) {
            List<Boolean> isCheckList = new ArrayList<>();
            for (PassLabel passLabel : passLabels) {
                boolean flag = false;
                if (passLabelArrayList != null) {
                    for (PassLabel label : passLabelArrayList) {
                        if (label.getUuid().equals(passLabel.getUuid())) {
                            flag = true;
                            break;
                        }
                    }
                }
                isCheckList.add(flag);
            }
            if (passLabels.size() < pageSize) {
                onLoadListener.onLoadSuccess(true, passLabels, isCheckList);
            } else {
                onLoadListener.onLoadSuccess(false, passLabels, isCheckList);
            }
        } else {
            onLoadListener.onLoadError(ResUtils.getString(R.string.error_load_failed));
        }
    }

}
