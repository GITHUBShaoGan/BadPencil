package com.slut.badpencil.main.fragment.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.main.fragment.m.PassModel;
import com.slut.badpencil.main.fragment.m.PassModelImpl;
import com.slut.badpencil.main.fragment.v.PassView;

import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public class PassPresenterImpl implements PassPresenter, PassModel.OnLoadMoreListener,
        PassModel.OnNotifyItemChangeListener, PassModel.OnNotifyItemInsertListener,
        PassModel.OnNotifyItemRemoveListener {

    private PassModel passModel;
    private PassView passView;

    public PassPresenterImpl(PassView passView) {
        this.passView = passView;
        this.passModel = new PassModelImpl();
    }

    @Override
    public void onLoadSuccess(int type, List<Password> passwordList, List<List<PassLabel>> passlabelLists) {
        passView.onLoadSuccess(type, passwordList, passlabelLists);
    }

    @Override
    public void onLoadError(String msg) {
        passView.onLoadError(msg);
    }

    @Override
    public void loadMore(long pageNo, long pageSize) {
        passModel.loadMore(pageNo, pageSize, this);
    }

    @Override
    public void notifyItemChanged(String uuid, List<Password> passwordList) {
        passModel.notifyItemChanged(uuid, passwordList, this);
    }

    @Override
    public void notifyItemInsert(String uuid) {
        passModel.notifyItemInsert(uuid, this);
    }

    @Override
    public void notifyItemRemove(String uuid,List<Password> passwordList) {
        passModel.notifyItemRemove(uuid,passwordList,this);
    }

    @Override
    public void notifyItemChangeSuccess(int position, Password password, List<PassLabel> passLabelList) {
        passView.notifyItemChangeSuccess(position, password, passLabelList);
    }

    @Override
    public void notifyItemChangeError(String msg) {
        passView.notifyItemChangeError(msg);
    }

    @Override
    public void notifyItemInsertSuccess(Password password, List<PassLabel> passLabelList) {
        passView.notifyItemInsertSuccess(password, passLabelList);
    }

    @Override
    public void notifyItemInsertError(String msg) {
        passView.notifyItemInsertError(msg);
    }

    @Override
    public void notifyItemRemoveSuccess(int position) {
        passView.notifyItemRemoveSuccess(position);
    }

    @Override
    public void notifyItemRemoveError(String msg) {
        passView.notifyItemRemoveError(msg);
    }
}
