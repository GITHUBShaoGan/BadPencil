package com.slut.badpencil.master.pattern.p;

import com.slut.badpencil.master.pattern.m.PatternInitModel;
import com.slut.badpencil.master.pattern.m.PatternInitModelImpl;
import com.slut.badpencil.master.pattern.v.PatternInitView;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */

public class PatternInitPresenterImpl implements PatternInitPresenter,PatternInitModel.OnCreateCallback,PatternInitModel.OnCheckConfigCallback {

    private PatternInitModel patternInitModel;
    private PatternInitView patternInitView;

    public PatternInitPresenterImpl(PatternInitView patternInitView) {
        this.patternInitView = patternInitView;
        this.patternInitModel = new PatternInitModelImpl();
    }

    @Override
    public void onCreateSuccess() {
        patternInitView.onCreateSuccess();
    }

    @Override
    public void onCreateError(String errorMsg) {
        patternInitView.onCreateError(errorMsg);
    }

    @Override
    public void create(String patternPassword) {
        patternInitModel.create(patternPassword,this);
    }

    @Override
    public void checkConfig() {
        patternInitModel.checkConfig(this);
    }

    @Override
    public void onConfigExists() {
        patternInitView.onConfigExists();
    }

    @Override
    public void onConfigNotExists() {
        patternInitView.onConfigNotExists();
    }
}
