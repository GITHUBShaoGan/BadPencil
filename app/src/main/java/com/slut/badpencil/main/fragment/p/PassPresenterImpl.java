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

public class PassPresenterImpl implements PassPresenter,PassModel.OnLoadMoreListener {

    private PassModel passModel;
    private PassView passView;

    public PassPresenterImpl(PassView passView) {
        this.passView = passView;
        this.passModel = new PassModelImpl();
    }

    @Override
    public void onLoadSuccess(int type, List<Password> passwordList, List<List<PassLabel>> passlabelLists) {
        passView.onLoadSuccess(type,passwordList,passlabelLists);
    }

    @Override
    public void onLoadError(String msg) {
        passView.onLoadError(msg);
    }

    @Override
    public void loadMore(long pageNo, long pageSize) {
        passModel.loadMore(pageNo,pageSize,this);
    }
}
