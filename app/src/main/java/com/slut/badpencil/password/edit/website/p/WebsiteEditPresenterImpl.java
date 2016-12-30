package com.slut.badpencil.password.edit.website.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;
import com.slut.badpencil.password.edit.website.m.WebsiteEditModel;
import com.slut.badpencil.password.edit.website.m.WebsiteEditModelImpl;
import com.slut.badpencil.password.edit.website.v.WebsiteEditView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class WebsiteEditPresenterImpl implements WebsiteEditPresenter, WebsiteEditModel.OnQueryLabelsListener, WebsiteEditModel.OnCheckUIListener, WebsiteEditModel.OnCreateListener, WebsiteEditModel.OnUpdateListener {

    private WebsiteEditModel websiteEditModel;
    private WebsiteEditView websiteEditView;

    public WebsiteEditPresenterImpl(WebsiteEditView websiteEditView) {
        this.websiteEditView = websiteEditView;
        this.websiteEditModel = new WebsiteEditModelImpl();
    }

    @Override
    public void onQuerySuccess(List<PassLabel> passLabelList) {
        websiteEditView.onQuerySuccess(passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        websiteEditView.onQueryError(msg);
    }

    @Override
    public void queryLabels(WebsitePassword websitePassword) {
        websiteEditModel.queryLabels(websitePassword, this);
    }

    @Override
    public void checkUI(WebsitePassword primaryPassword, String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, ArrayList<PassLabel> primaryLabelList) {
        websiteEditModel.checkUI(primaryPassword, title, account, password, url, remark, passLabelArrayList, primaryLabelList, this);
    }

    @Override
    public void create(String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList) {
        websiteEditModel.create(title, account, password, url, remark, passLabelArrayList, this);
    }

    @Override
    public void update(WebsitePassword websitePassword, String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabels) {
        websiteEditModel.update(websitePassword, title, account, password, url, remark, passLabels, this);
    }

    @Override
    public void onUIChange() {
        websiteEditView.onUIChange();
    }

    @Override
    public void onUINotChange() {
        websiteEditView.onUINotChange();
    }

    @Override
    public void onInputInvalid() {
        websiteEditView.onInputInvalid();
    }

    @Override
    public void onCreateSuccess(WebsitePassword password) {
        websiteEditView.onCreateSuccess(password);
    }

    @Override
    public void onCreateEmptyTitle() {
        websiteEditView.onCreateEmptyTitle();
    }

    @Override
    public void onCreateEmptyAccount() {
        websiteEditView.onCreateEmptyAccount();
    }

    @Override
    public void onCreateEmptyPassword() {
        websiteEditView.onCreateEmptyPassword();
    }

    @Override
    public void onCreateError(String msg) {
        websiteEditView.onCreateError(msg);
    }

    @Override
    public void onUpdateSuccess() {
        websiteEditView.onUpdateSuccess();
    }

    @Override
    public void onUpdateEmptyTitle() {
        websiteEditView.onUpdateEmptyTitle();
    }

    @Override
    public void onUpdateEmptyAccount() {
        websiteEditView.onUpdateEmptyAccount();
    }

    @Override
    public void onUpdateEmptyPassword() {
        websiteEditView.onUpdateEmptyPassword();
    }

    @Override
    public void onUpdateError(String msg) {
        websiteEditView.onUpdateError(msg);
    }
}
