package com.slut.badpencil.password.show.wifi.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;
import com.slut.badpencil.password.show.wifi.m.PassWifiModel;
import com.slut.badpencil.password.show.wifi.m.PassWifiModelImpl;
import com.slut.badpencil.password.show.wifi.v.PassWifiView;

import java.util.List;

/**
 * Created by shijianan on 2017/1/11.
 */

public class PassWifiPresenterImpl implements PassWifiPresenter, PassWifiModel.OnQueryListener,PassWifiModel.OnDeleteListener {

    private PassWifiModel passWifiModel;
    private PassWifiView passWifiView;

    public PassWifiPresenterImpl(PassWifiView passWifiView) {
        this.passWifiView = passWifiView;
        this.passWifiModel = new PassWifiModelImpl();
    }

    @Override
    public void onQuerySuccess(Password password, WifiPassword wifiPassword, List<PassLabel> passLabelList) {
        passWifiView.onQuerySuccess(password, wifiPassword, passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        passWifiView.onQueryError(msg);
    }

    @Override
    public void query(String uuid) {
        passWifiModel.query(uuid, this);
    }

    @Override
    public void delete(String uuid) {
        passWifiModel.delete(uuid,this);
    }

    @Override
    public void onDeleteSuccess(String uuid) {
        passWifiView.onDeleteSuccess(uuid);
    }

    @Override
    public void onDeleteError(String msg) {
        passWifiView.onDeleteError(msg);
    }
}
