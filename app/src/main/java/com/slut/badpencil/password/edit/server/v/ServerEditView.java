package com.slut.badpencil.password.edit.server.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public interface ServerEditView {

    void onQuerySuccess(Password password, ServerPassword serverPassword, List<PassLabel> passLabelList);

    void onQueryError(String msg);


    void onUIChange();

    void onUINotChange();

    void onInputInvalid();


    void onInsertSuccess(ServerPassword serverPassword);

    void onInsertEmptyTitle();

    void onInsertIllegalIP();

    void onInsertIllegalPort();

    void onInsertEmptyAccount();

    void onInsertEmptyPassword();

    void onInsertError(String msg);


    void onUpdateSuccess(String uuid);

    void onUpdateEmptyTitle();

    void onUpdateIllegalIP();

    void onUpdateIllegalPort();

    void onUpdateEmptyAccount();

    void onUpdateEmptyPassword();

    void onUpdateError(String msg);

}
