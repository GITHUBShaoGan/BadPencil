package com.slut.badpencil.master.pattern.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.UserConfig;
import com.slut.badpencil.database.dao.UserConfigDao;
import com.slut.badpencil.utils.ResUtils;

import java.sql.SQLException;
import java.util.UUID;

import butterknife.OnClick;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */

public class PatternInitModelImpl implements PatternInitModel {

    @Override
    public void create(String patternPassword, OnCreateCallback onCreateCallback) {
        if (TextUtils.isEmpty(patternPassword)) {
            onCreateCallback.onCreateError(ResUtils.getString(R.string.error_pattern_create_empty));
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        UserConfig userConfig = new UserConfig(uuid, true, patternPassword, false, "", UserConfig.UnlockType.PATTERN, stamp, stamp);
        try {
            if (!UserConfigDao.getInstances().isConfigExists()) {
                UserConfigDao.getInstances().createSingle(userConfig);
                onCreateCallback.onCreateSuccess();
            } else {
                onCreateCallback.onCreateError(ResUtils.getString(R.string.error_pattern_create_exists));
            }
        } catch (SQLException e) {
            onCreateCallback.onCreateError(e.getLocalizedMessage());
        }

    }

    @Override
    public void checkConfig(OnCheckConfigCallback onCheckCallback) {
        try {
            if (!UserConfigDao.getInstances().isConfigExists()) {
                onCheckCallback.onConfigNotExists();
            } else {
                onCheckCallback.onConfigExists();
            }
        } catch (SQLException e) {

        }
    }

}
