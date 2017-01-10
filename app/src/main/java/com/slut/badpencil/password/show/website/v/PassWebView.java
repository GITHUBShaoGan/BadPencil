package com.slut.badpencil.password.show.website.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;

import java.util.List;

/**
 * Created by shijianan on 2017/1/10.
 */

public interface PassWebView {

    void onQuerySuccess(Password password, WebsitePassword websitePassword, List<PassLabel> passLabelList);

    void onQueryError(String msg);

}
