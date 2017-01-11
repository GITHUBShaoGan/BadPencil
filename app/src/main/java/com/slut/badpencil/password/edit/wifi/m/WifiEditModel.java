package com.slut.badpencil.password.edit.wifi.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/11.
 */

public interface WifiEditModel {

    interface OnQueryListener{

        void onQuerySuccess(Password password, WifiPassword wifiPassword, List<PassLabel> passLabelList);

        void onQueryError(String msg);

    }

    void query(String uuid,OnQueryListener onQueryListener);

    interface OnCheckUIListener{

        void onUIChange();

        void onUINotChange();

        void onInputInvalid();

    }

    void checkUI(WifiPassword primaryPassObj,String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> primaryLabels,ArrayList<PassLabel> extraLabels,OnCheckUIListener onCheckUIListener);

    interface OnInsertListener{

        void onInsertEmptyTitle();

        void onInsertEmptyPassword();

        void onInsertSuccess(String uuid);

        void onInsertError(String msg);

    }

    void insert(String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels,OnInsertListener onInsertListener);

    interface OnUpdateListener{

        void onUpdateEmptyTitle();

        void onUpdateEmptyPassword();

        void onUpdateSuccess(String uuid);

        void onUpdateError(String msg);

    }

    void update(String uuid,String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels,OnUpdateListener onUpdateListener);
}
