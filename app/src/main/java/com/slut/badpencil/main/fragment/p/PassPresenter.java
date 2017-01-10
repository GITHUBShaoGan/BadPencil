package com.slut.badpencil.main.fragment.p;

import com.slut.badpencil.database.bean.password.Password;

import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public interface PassPresenter {

    void loadMore(long pageNo,long pageSize);

    void notifyItemChanged(String uuid,List<Password> passwordList);

    void notifyItemInsert(String uuid);

    void notifyItemRemove(String uuid,List<Password> passwordList);

}
