package com.slut.badpencil.password.label.v;

import com.slut.badpencil.database.bean.password.PassLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public interface LabelView {

    void onLoadSuccess(boolean isCompleted, List<PassLabel> passLabelList,List<Boolean> isCheckList);

    void onLoadError(String msg);

    void onCreateSuccess(PassLabel passLabel);

    void onCreateExists();

    void onCreateError(String msg);

}
