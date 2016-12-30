package com.slut.badpencil.password.edit.original.v;

import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public interface PassEditView {

    void onUIChange();

    void onUINotChange();

    void onInputInvalid();


    void onCreateSuccess(Password password);

    void onCreateEmptyTitle();

    void onCreateEmptyAccount();

    void onCreateEmptyPassword();

    void onCreateError(String msg);

}
