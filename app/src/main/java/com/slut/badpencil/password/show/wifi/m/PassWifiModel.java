package com.slut.badpencil.password.show.wifi.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/11.
 */

public interface PassWifiModel {

    interface OnQueryListener{

        void onQuerySuccess(Password password, WifiPassword wifiPassword,List<PassLabel> passLabelList);

        void onQueryError(String msg);

    }

    void query(String uuid,OnQueryListener onQueryListener);

    interface OnDeleteListener{

        void onDeleteSuccess(String uuid);

        void onDeleteError(String msg);

    }

    void delete(String uuid,OnDeleteListener onDeleteListener);

}
