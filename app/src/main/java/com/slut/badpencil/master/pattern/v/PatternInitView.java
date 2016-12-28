package com.slut.badpencil.master.pattern.v;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */

public interface PatternInitView {

    void onCreateSuccess();

    void onCreateError(String errorMsg);

    void onConfigExists();

    void onConfigNotExists();

}
