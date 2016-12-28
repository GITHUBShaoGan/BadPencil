package com.slut.badpencil.master.pattern.m;

import java.sql.SQLException;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */

public interface PatternInitModel {

    interface OnCreateCallback {

        void onCreateSuccess();

        void onCreateError(String errorMsg);

    }

    void create(String patternPassword,OnCreateCallback onCreateCallback);

    interface OnCheckConfigCallback{

        void onConfigExists();

        void onConfigNotExists();

    }

    void checkConfig(OnCheckConfigCallback onCheckCallback);

}
