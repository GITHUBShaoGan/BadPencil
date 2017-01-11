package com.slut.badpencil.password.show.wifi.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/11.
 */

public interface PassWifiView {

    void onQuerySuccess(Password password, WifiPassword wifiPassword, List<PassLabel> passLabelList);

    void onQueryError(String msg);

    void onDeleteSuccess(String uuid);

    void onDeleteError(String msg);

}
