package com.slut.badpencil.password.label.m;

import com.slut.badpencil.database.bean.password.PassLabel;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public interface LabelModel {

    interface OnCreateListener {

        void onCreateSuccess(PassLabel passLabel);

        void onCreateExists();

        void onCreateError(String msg);

    }

    void create(String name,OnCreateListener onCreateListener);

    interface OnLoadListener {

        void onLoadSuccess(boolean isCompleted, List<PassLabel> passLabelList);

        void onLoadError(String msg);

    }

    void load(long pageNO, long pageSize, OnLoadListener onLoadListener);

}
