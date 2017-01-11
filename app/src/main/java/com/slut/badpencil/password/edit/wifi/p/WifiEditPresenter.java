package com.slut.badpencil.password.edit.wifi.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.WifiPassword;

import java.util.ArrayList;

/**
 * Created by shijianan on 2017/1/11.
 */

public interface WifiEditPresenter {

    void query(String uuid);

    void checkUI(WifiPassword primaryPassObj, String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> primaryLabels, ArrayList<PassLabel> extraLabels);

    void insert(String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels);

    void update(String uuid,String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels);

}
