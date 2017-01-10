package com.slut.badpencil.password.edit.server.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public interface ServerEditModel {

    interface OnQueryListener {

        void onQuerySuccess(Password password,ServerPassword serverPassword,List<PassLabel> passLabelList);

        void onQueryError(String msg);

    }

    void query(String uuid, OnQueryListener onQueryListener);


    interface OnCheckUIListener{

        void onUIChange();

        void onUINotChange();

        void onInputInvalid();

    }

    void checkUI(ServerPassword primaryPassword, String title, String address, String port, String account, String password, String remark, ArrayList<PassLabel> primaryLabels,ArrayList<PassLabel> extraLabels,OnCheckUIListener onCheckUIListener);


    interface OnInsertListener{

        void onInsertSuccess(ServerPassword serverPassword);

        void onInsertEmptyTitle();

        void onInsertIllegalIP();

        void onInsertIllegalPort();

        void onInsertEmptyAccount();

        void onInsertEmptyPassword();

        void onInsertError(String msg);

    }

    void insert(String title,String ip,String port,String account,String password,String remark,ArrayList<PassLabel> passLabelArrayList,OnInsertListener onInsertListener);


    interface OnUpdateListener{

        void onUpdateSuccess(String uuid);

        void onUpdateEmptyTitle();

        void onUpdateIllegalIP();

        void onUpdateIllegalPort();

        void onUpdateEmptyAccount();

        void onUpdateEmptyPassword();

        void onUpdateError(String msg);

    }

    void update(ServerPassword serverPassword,String title,String ip,String port,String account,String password,String remark,ArrayList<PassLabel> passLabelArrayList,OnUpdateListener onUpdateListener);

}
