package com.slut.badpencil.password.edit.original.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public interface PassEditPresenter {

    void create(String title, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList);

    void checkUI(Password primaryPassword, String title, String account, String password, String remark,ArrayList<PassLabel> passLabels,ArrayList<PassLabel> primaryPassLabels);

    void queryLabels(Password password);

}
