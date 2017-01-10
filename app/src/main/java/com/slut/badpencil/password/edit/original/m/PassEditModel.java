package com.slut.badpencil.password.edit.original.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public interface PassEditModel {

    interface OnCheckUICallback {

        void onUIChange();

        void onUINotChange();

        void onInputInvalid();

    }

    void checkUI(Password primaryPassword, String title, String account, String password, String remark,ArrayList<PassLabel> passLabels,ArrayList<PassLabel> primaryPassLabels, OnCheckUICallback onCheckUICallback);

    interface OnCreateCallback {

        void onCreateSuccess(Password password);

        void onCreateEmptyTitle();

        void onCreateEmptyAccount();

        void onCreateEmptyPassword();

        void onCreateError(String msg);

    }

    void create(String title, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList, OnCreateCallback onCreateCallback);

    interface OnQueryLabelListener{

        void onQuerySuccess(Password password,List<PassLabel> passLabelList);

        void onQueryError(String msg);

    }

    void queryLabels(String uuid,OnQueryLabelListener onQueryLabelListener);


    interface OnUpdateListener{

        void onUpdateSuccess(Password password);

        void onUpdateEmptyTitle();

        void onUpdateEmptyAccount();

        void onUpdateEmptyPassword();

        void onUpdateError(String msg);

    }

    void update(Password password,String title,String account,String pass,String remark,ArrayList<PassLabel> passLabelArrayList,OnUpdateListener onUpdateListener);
}
