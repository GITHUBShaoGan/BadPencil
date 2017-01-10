package com.slut.badpencil.password.show.website.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;
import com.slut.badpencil.password.show.website.m.PassWebModel;
import com.slut.badpencil.password.show.website.m.PassWebModelImpl;
import com.slut.badpencil.password.show.website.v.PassWebView;

import java.util.List;

/**
 * Created by shijianan on 2017/1/10.
 */

public class PassWebPresenterImpl implements PassWebPresenter, PassWebModel.OnQueryListener {

    private PassWebModel passWebModel;
    private PassWebView passWebView;

    public PassWebPresenterImpl(PassWebView passWebView) {
        this.passWebView = passWebView;
        this.passWebModel = new PassWebModelImpl();
    }

    @Override
    public void onQuerySuccess(Password password, WebsitePassword websitePassword, List<PassLabel> passLabelList) {
        passWebView.onQuerySuccess(password, websitePassword, passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        passWebView.onQueryError(msg);
    }

    @Override
    public void query(String uuid) {
        passWebModel.query(uuid, this);
    }
}
