package com.slut.badpencil.main.fragment.m;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;

import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public interface PassModel {

    interface OnLoadMoreListener{

        void onLoadSuccess(int type,List<Password> passwordList,List<List<PassLabel>> passlabelLists);

        void onLoadError(String msg);

    }

    void loadMore(long pageNo,long pageSize,OnLoadMoreListener onLoadMoreListener);

}
