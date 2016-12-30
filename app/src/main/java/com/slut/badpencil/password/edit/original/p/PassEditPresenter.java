package com.slut.badpencil.password.edit.original.p;

import com.slut.badpencil.database.bean.password.Password;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public interface PassEditPresenter {

    void create(String title, String account, String password, String remark);

    void checkUI(Password primaryPassword, String title, String account, String password, String remark);

}
