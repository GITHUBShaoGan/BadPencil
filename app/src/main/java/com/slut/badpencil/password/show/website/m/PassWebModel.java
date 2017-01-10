package com.slut.badpencil.password.show.website.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/10.
 */

public interface PassWebModel {

    interface OnQueryListener{

        void onQuerySuccess(Password password, WebsitePassword websitePassword, List<PassLabel> passLabelList);

        void onQueryError(String msg);

    }

    void query(String uuid,OnQueryListener onQueryListener);

    interface OnDeleteListener{

        void onDeleteSuccess(String uuid);

        void onDeleteError(String msg);

    }

    void delete(String uuid,OnDeleteListener onDeleteListener);
}
