package com.slut.badpencil.password.label.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.password.label.m.LabelModel;
import com.slut.badpencil.password.label.m.LabelModelImpl;
import com.slut.badpencil.password.label.v.LabelView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class LabelPresenterImpl implements LabelPresenter, LabelModel.OnLoadListener,LabelModel.OnCreateListener {

    private LabelModel labelModel;
    private LabelView labelView;

    public LabelPresenterImpl(LabelView labelView) {
        this.labelView = labelView;
        this.labelModel = new LabelModelImpl();
    }

    @Override
    public void onLoadSuccess(boolean isCompleted, List<PassLabel> passLabelList) {
        labelView.onLoadSuccess(isCompleted, passLabelList);
    }

    @Override
    public void onLoadError(String msg) {
        labelView.onLoadError(msg);
    }

    @Override
    public void load(long pageNO, long pageSize) {
        labelModel.load(pageNO, pageSize, this);
    }

    @Override
    public void create(String name) {
        labelModel.create(name,this);
    }

    @Override
    public void onCreateSuccess(PassLabel passLabel) {
        labelView.onCreateSuccess(passLabel);
    }

    @Override
    public void onCreateExists() {
        labelView.onCreateExists();
    }

    @Override
    public void onCreateError(String msg) {
        labelView.onCreateError(msg);
    }
}
