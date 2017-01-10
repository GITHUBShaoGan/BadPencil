package com.slut.badpencil.password.edit.server.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.ServerPassword;

import java.util.ArrayList;

/**
 * Created by shijianan on 2017/1/4.
 */

public interface ServerEditPresenter {

    void queryLabel(String uuid);

    void checkUI(ServerPassword primaryPassword, String title, String address, String port, String account, String password, String remark, ArrayList<PassLabel> primaryLabels, ArrayList<PassLabel> extraLabels);

    void insert(String title,String ip,String port,String account,String password,String remark,ArrayList<PassLabel> passLabelArrayList);

    void update(ServerPassword serverPassword,String title,String ip,String port,String account,String password,String remark,ArrayList<PassLabel> passLabelArrayList);


}
