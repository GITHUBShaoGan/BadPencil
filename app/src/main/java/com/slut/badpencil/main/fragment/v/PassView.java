package com.slut.badpencil.main.fragment.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;

import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public interface PassView {

    void onLoadSuccess(int type, List<Password> passwordList, List<List<PassLabel>> passlabelLists);

    void onLoadError(String msg);

}
