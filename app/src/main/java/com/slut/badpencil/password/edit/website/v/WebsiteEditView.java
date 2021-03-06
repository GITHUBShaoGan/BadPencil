package com.slut.badpencil.password.edit.website.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public interface WebsiteEditView {

    void onQuerySuccess(Password password, WebsitePassword websitePassword,List<PassLabel> passLabelList);

    void onQueryError(String msg);

    void onUIChange();

    void onUINotChange();

    void onInputInvalid();

    void onCreateSuccess(WebsitePassword password);

    void onCreateEmptyTitle();

    void onCreateEmptyAccount();

    void onCreateEmptyPassword();

    void onCreateError(String msg);

    void onUpdateSuccess(WebsitePassword websitePassword);

    void onUpdateEmptyTitle();

    void onUpdateEmptyAccount();

    void onUpdateEmptyPassword();

    void onUpdateError(String msg);

}
