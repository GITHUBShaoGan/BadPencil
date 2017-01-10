package com.slut.badpencil.password.show.server.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/6.
 */

public interface PassServerModel {

    interface OnQueryListener {

        void onQuerySuccess(Password password, ServerPassword serverPassword, List<PassLabel> passLabelList);

        void onQueryError(String msg);

    }

    void query(String uuid, OnQueryListener onQueryListener);

    interface OnDeleteListener{

        void onDeleteSuccess(String uuid);

        void onDeleteError(String msg);

    }

    void delete(String uuid,OnDeleteListener onDeleteListener);

}
