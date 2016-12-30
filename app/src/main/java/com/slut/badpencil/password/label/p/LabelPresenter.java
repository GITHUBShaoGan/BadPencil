package com.slut.badpencil.password.label.p;

import com.slut.badpencil.database.bean.password.PassLabel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public interface LabelPresenter {

    void load(long pageNO, long pageSize, ArrayList<PassLabel> passLabelArrayList);

    void create(String name);

}
