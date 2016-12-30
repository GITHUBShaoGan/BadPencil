package com.slut.badpencil.password.edit.original.p;

import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.password.edit.original.m.PassEditModel;
import com.slut.badpencil.password.edit.original.m.PassEditModelImpl;
import com.slut.badpencil.password.edit.original.v.PassEditView;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class PassEditPresenterImpl implements PassEditPresenter, PassEditModel.OnCreateCallback,PassEditModel.OnCheckUICallback {

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
    public void create(String title, String account, String password, String remark) {
        passEditModel.create(title, account, password, remark, this);
    }

    @Override
    public void checkUI(Password primaryPassword, String title, String account, String password, String remark) {
        passEditModel.checkUI(primaryPassword,title,account,password,remark,this);
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
}
