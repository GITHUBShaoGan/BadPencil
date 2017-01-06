package com.slut.badpencil.password.show.server.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/6.
 */

public interface PassServerView {

    void onQuerySuccess(Password password, ServerPassword serverPassword, List<PassLabel> passLabelList);

    void onQueryError(String msg);

}
