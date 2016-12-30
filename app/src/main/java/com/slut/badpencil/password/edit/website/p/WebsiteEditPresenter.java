package com.slut.badpencil.password.edit.website.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.WebsitePassword;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public interface WebsiteEditPresenter {

    void queryLabels(WebsitePassword websitePassword);

    void checkUI(WebsitePassword primaryPassword, String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, ArrayList<PassLabel> primaryLabelList);

    void create(String title,String account,String password,String url,String remark,ArrayList<PassLabel> passLabelArrayList);

}
