package com.slut.badpencil.password.show.original.v;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;

import java.util.List;

/**
 * Created by shijianan on 2017/1/10.
 */

public interface PassOriginalView {

    void onQuerySuccess(Password password, List<PassLabel> passLabelList);

    void onQueryError(String msg);

    void onDeleteSuccess(String uuid);

    void onDeleteError(String msg);

}
