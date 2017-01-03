package com.slut.badpencil.password.edit.original.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.password.edit.original.m.PassEditModel;
import com.slut.badpencil.password.edit.original.m.PassEditModelImpl;
import com.slut.badpencil.password.edit.original.v.PassEditView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class PassEditPresenterImpl implements PassEditPresenter, PassEditModel.OnCreateCallback, PassEditModel.OnCheckUICallback, PassEditModel.OnQueryLabelListener,PassEditModel.OnUpdateListener {

    private PassEditModel passEditModel;
    private PassEditView passEditView;

    public PassEditPresenterImpl(PassEditView passEditView) {
        this.passEditView = passEditView;
        this.passEditModel = new PassEditModelImpl();
    }

    @Override
    public void onCreateSuccess(Password password) {
        passEditView.onCreateSuccess(password);
    }

    @Override
    public void onCreateEmptyTitle() {
        passEditView.onCreateEmptyTitle();
    }

    @Override
    public void onCreateEmptyAccount() {
        passEditView.onCreateEmptyAccount();
    }

    @Override
    public void onCreateEmptyPassword() {
        passEditView.onCreateEmptyPassword();
    }

    @Override
    public void onCreateError(String msg) {
        passEditView.onCreateError(msg);
    }

    @Override
    public void create(String title, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList) {
        passEditModel.create(title, account, password, remark, passLabelArrayList, this);
    }

    @Override
    public void checkUI(Password primaryPassword, String title, String account, String password, String remark,ArrayList<PassLabel> passLabels,ArrayList<PassLabel> primaryPassLabels) {
        passEditModel.checkUI(primaryPassword, title, account, password, remark, passLabels,primaryPassLabels,this);
    }

    @Override
    public void queryLabels(Password password) {
        passEditModel.queryLabels(password,this);
    }

    @Override
    public void update(Password password, String title, String account, String pass, String remark, ArrayList<PassLabel> passLabelArrayList) {
        passEditModel.update(password,title,account,pass,remark,passLabelArrayList,this);
    }

    @Override
    public void onUIChange() {
        passEditView.onUIChange();
    }

    @Override
    public void onUINotChange() {
        passEditView.onUINotChange();
    }

    @Override
    public void onInputInvalid() {
        passEditView.onInputInvalid();
    }

    @Override
    public void onQuerySuccess(List<PassLabel> passLabelList) {
        passEditView.onQuerySuccess(passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        passEditView.onQueryError(msg);
    }

    @Override
    public void onUpdateSuccess(Password password) {
        passEditView.onUpdateSuccess(password);
    }

    @Override
    public void onUpdateEmptyTitle() {
        passEditView.onUpdateEmptyTitle();
    }

    @Override
    public void onUpdateEmptyAccount() {
        passEditView.onUpdateEmptyAccount();
    }

    @Override
    public void onUpdateEmptyPassword() {
        passEditView.onUpdateEmptyPassword();
    }

    @Override
    public void onUpdateError(String msg) {
        passEditView.onUpdateError(msg);
    }
}
