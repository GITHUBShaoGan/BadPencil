package com.slut.badpencil.password.edit.wifi.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;
import com.slut.badpencil.password.edit.wifi.m.WifiEditModel;
import com.slut.badpencil.password.edit.wifi.m.WifiEditModelImpl;
import com.slut.badpencil.password.edit.wifi.v.WifiEditView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/11.
 */

public class WifiEditPresenterImpl implements WifiEditPresenter, WifiEditModel.OnQueryListener, WifiEditModel.OnCheckUIListener, WifiEditModel.OnInsertListener, WifiEditModel.OnUpdateListener {

    private WifiEditModel wifiEditModel;
    private WifiEditView wifiEditView;

    public WifiEditPresenterImpl(WifiEditView wifiEditView) {
        this.wifiEditView = wifiEditView;
        this.wifiEditModel = new WifiEditModelImpl();
    }

    @Override
    public void onQuerySuccess(Password password, WifiPassword wifiPassword, List<PassLabel> passLabelList) {
        wifiEditView.onQuerySuccess(password, wifiPassword, passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        wifiEditView.onQueryError(msg);
    }

    @Override
    public void query(String uuid) {
        wifiEditModel.query(uuid, this);
    }

    @Override
    public void checkUI(WifiPassword primaryPassObj, String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> primaryLabels, ArrayList<PassLabel> extraLabels) {
        wifiEditModel.checkUI(primaryPassObj, title, name, pswd, rtAddr, rtPswd, opAccount, opPswd, remark, primaryLabels, extraLabels, this);
    }

    @Override
    public void insert(String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels) {
        wifiEditModel.insert(title, name, pswd, rtAddr, rtPswd, opAccount, opPswd, remark, extraLabels, this);
    }

    @Override
    public void update(String uuid, String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels) {
        wifiEditModel.update(uuid, title, name, pswd, rtAddr, rtPswd, opAccount, opPswd, remark, extraLabels, this);
    }

    @Override
    public void onUIChange() {
        wifiEditView.onUIChange();
    }

    @Override
    public void onUINotChange() {
        wifiEditView.onUINotChange();
    }

    @Override
    public void onInputInvalid() {
        wifiEditView.onInputInvalid();
    }

    @Override
    public void onInsertEmptyTitle() {
        wifiEditView.onInsertEmptyTitle();
    }

    @Override
    public void onInsertEmptyPassword() {
        wifiEditView.onInsertEmptyPassword();
    }

    @Override
    public void onInsertSuccess(String uuid) {
        wifiEditView.onInsertSuccess(uuid);
    }

    @Override
    public void onInsertError(String msg) {
        wifiEditView.onInsertError(msg);
    }

    @Override
    public void onUpdateEmptyTitle() {
        wifiEditView.onUpdateEmptyTitle();
    }

    @Override
    public void onUpdateEmptyPassword() {
        wifiEditView.onUpdateEmptyPassword();
    }

    @Override
    public void onUpdateSuccess(String uuid) {
        wifiEditView.onUpdateSuccess(uuid);
    }

    @Override
    public void onUpdateError(String msg) {
        wifiEditView.onUpdateError(msg);
    }
}
