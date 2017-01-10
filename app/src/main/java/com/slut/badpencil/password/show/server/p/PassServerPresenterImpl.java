package com.slut.badpencil.password.show.server.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.password.show.server.m.PassServerModel;
import com.slut.badpencil.password.show.server.m.PassServerModelImpl;
import com.slut.badpencil.password.show.server.v.PassServerView;

import java.util.List;

/**
 * Created by shijianan on 2017/1/6.
 */

public class PassServerPresenterImpl implements PassServerPresenter,PassServerModel.OnQueryListener,PassServerModel.OnDeleteListener {

    private PassServerModel passServerModel;
    private PassServerView passServerView;

    public PassServerPresenterImpl(PassServerView passServerView) {
        this.passServerView = passServerView;
        this.passServerModel = new PassServerModelImpl();
    }

    @Override
    public void onQuerySuccess(Password password, ServerPassword serverPassword, List<PassLabel> passLabelList) {
        this.passServerView.onQuerySuccess(password,serverPassword,passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        passServerView.onQueryError(msg);
    }

    @Override
    public void query(String uuid) {
        passServerModel.query(uuid,this);
    }

    @Override
    public void delete(String uuid) {
        passServerModel.delete(uuid,this);
    }

    @Override
    public void onDeleteSuccess(String uuid) {
        passServerView.onDeleteSuccess(uuid);
    }

    @Override
    public void onDeleteError(String uuid) {
        passServerView.onDeleteError(uuid);
    }
}
