package com.slut.badpencil.password.show.original.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.password.show.original.m.PassOriginalModel;
import com.slut.badpencil.password.show.original.m.PassOriginalModelImpl;
import com.slut.badpencil.password.show.original.v.PassOriginalView;

import java.util.List;

/**
 * Created by shijianan on 2017/1/10.
 */

public class PassOriginalPresenterImpl implements PassOriginalPresenter, PassOriginalModel.OnQueryListener,PassOriginalModel.OnDeleteListener {

    private PassOriginalModel passOriginalModel;
    private PassOriginalView passOriginalView;

    public PassOriginalPresenterImpl(PassOriginalView passOriginalView) {
        this.passOriginalView = passOriginalView;
        this.passOriginalModel = new PassOriginalModelImpl();
    }

    @Override
    public void onQuerySuccess(Password password, List<PassLabel> passLabelList) {
        passOriginalView.onQuerySuccess(password, passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        passOriginalView.onQueryError(msg);
    }

    @Override
    public void query(String uuid) {
        passOriginalModel.query(uuid, this);
    }

    @Override
    public void delete(String uuid) {
        passOriginalModel.delete(uuid,this);
    }

    @Override
    public void onDeleteSuccess(String uuid) {
        passOriginalView.onDeleteSuccess(uuid);
    }

    @Override
    public void onDeleteError(String msg) {
        passOriginalView.onDeleteError(msg);
    }
}
