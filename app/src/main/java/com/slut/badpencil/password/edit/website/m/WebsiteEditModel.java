package com.slut.badpencil.password.edit.website.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public interface WebsiteEditModel {

    interface OnQueryLabelsListener {

        void onQuerySuccess(List<PassLabel> passLabelList);

        void onQueryError(String msg);
    }

    void queryLabels(WebsitePassword websitePassword, OnQueryLabelsListener onQueryLabelsListener);

    interface OnCheckUIListener {

        void onUIChange();

        void onUINotChange();

        void onInputInvalid();

    }

    void checkUI(WebsitePassword primaryPassword, String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, ArrayList<PassLabel> primaryLabelList, OnCheckUIListener onCheckUIListener);

    interface OnCreateListener {

        void onCreateSuccess(WebsitePassword password);

        void onCreateEmptyTitle();

        void onCreateEmptyAccount();

        void onCreateEmptyPassword();

        void onCreateError(String msg);

    }

    void create(String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, OnCreateListener onCreateListener);

    interface OnUpdateListener {

        void onUpdateSuccess(WebsitePassword websitePassword);

        void onUpdateEmptyTitle();

        void onUpdateEmptyAccount();

        void onUpdateEmptyPassword();

        void onUpdateError(String msg);

    }

    void update(WebsitePassword websitePassword,String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabels, OnUpdateListener onUpdateListener);

}
