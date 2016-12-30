package com.slut.badpencil.password.edit.original.m;

import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public interface PassEditModel {

    interface OnCheckUICallback {

        void onUIChange();

        void onUINotChange();

        void onInputInvalid();

    }

    void checkUI(Password primaryPassword, String title, String account, String password, String remark, OnCheckUICallback onCheckUICallback);

    interface OnCreateCallback {

        void onCreateSuccess(Password password);

        void onCreateEmptyTitle();

        void onCreateEmptyAccount();

        void onCreateEmptyPassword();

        void onCreateError(String msg);

    }

    void create(String title, String account, String password, String remark, OnCreateCallback onCreateCallback);

}
