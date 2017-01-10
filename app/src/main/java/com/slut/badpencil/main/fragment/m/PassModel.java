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

    interface OnNotifyItemChangeListener{

        void notifyItemChangeSuccess(int position,Password password,List<PassLabel> passLabelList);

        void notifyItemChangeError(String msg);

    }

    void notifyItemChanged(String uuid,List<Password> passwordList,OnNotifyItemChangeListener onNotifyItemChangeListener);

    interface OnNotifyItemInsertListener{

        void notifyItemInsertSuccess(Password password,List<PassLabel> passLabelList);

        void notifyItemInsertError(String msg);

    }

    void notifyItemInsert(String uuid,OnNotifyItemInsertListener onNotifyItemInsertListener);

    interface OnNotifyItemRemoveListener{

        void notifyItemRemoveSuccess(int position);

        void notifyItemRemoveError(String msg);

    }

    void notifyItemRemove(String uuid,List<Password> passwordList,OnNotifyItemRemoveListener onNotifyItemRemoveListener);
}
