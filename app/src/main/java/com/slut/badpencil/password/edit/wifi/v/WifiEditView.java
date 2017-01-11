package com.slut.badpencil.password.edit.wifi.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/11.
 */

public interface WifiEditView {

    void onQuerySuccess(Password password, WifiPassword wifiPassword, List<PassLabel> passLabelList);

    void onQueryError(String msg);

    void onUIChange();

    void onUINotChange();

    void onInputInvalid();

    void onInsertEmptyTitle();

    void onInsertEmptyPassword();

    void onInsertSuccess(String uuid);

    void onInsertError(String msg);

    void onUpdateEmptyTitle();

    void onUpdateEmptyPassword();

    void onUpdateSuccess(String uuid);

    void onUpdateError(String msg);

}
